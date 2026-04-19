package com.brainware.simplebankingapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    /**
     * Fetches the current balance for a customer.
     */
    public Double getBalanceByCustomerId(int customerId) {
        String query = "SELECT balance FROM accounts WHERE customer_id = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches the internal account_id for a customer.
     */
    public Integer getAccountIdByCustomerId(int customerId) {
        String query = "SELECT account_id FROM accounts WHERE customer_id = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("account_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * INTERNAL HELPER: Gets the account_id using an EXISTING connection.
     * This prevents deadlocks and connection leaks during transactions.
     */
    private Integer getAccountIdInternal(Connection conn, int customerId) throws SQLException {
        String query = "SELECT account_id FROM accounts WHERE customer_id = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("account_id");
            }
        }
        return null;
    }

    /**
     * Handles cash deposits.
     */
    public boolean deposit(int customerId, double amount) {
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE customer_id = ?";
        String insertQuery = "INSERT INTO transactions (account_id, amount, type, description) VALUES (?, ?, 'DEPOSIT', ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start Transaction

            try (PreparedStatement ps1 = conn.prepareStatement(updateQuery)) {
                ps1.setDouble(1, amount);
                ps1.setInt(2, customerId);
                if (ps1.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            Integer accountId = getAccountIdInternal(conn, customerId); // Use internal helper
            if (accountId == null) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement ps2 = conn.prepareStatement(insertQuery)) {
                ps2.setInt(1, accountId);
                ps2.setDouble(2, amount);
                ps2.setString(3, "Cash deposit");
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Handles cash withdrawals (checks balance first).
     */
    public boolean withdraw(int customerId, double amount) {
        String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE customer_id = ? AND balance >= ?";
        String insertQuery = "INSERT INTO transactions (account_id, amount, type, description) VALUES (?, ?, 'WITHDRAW', ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(updateQuery)) {
                ps1.setDouble(1, amount);
                ps1.setInt(2, customerId);
                ps1.setDouble(3, amount); // Must have at least 'amount' in balance
                if (ps1.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            Integer accountId = getAccountIdInternal(conn, customerId);
            if (accountId == null) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement ps2 = conn.prepareStatement(insertQuery)) {
                ps2.setInt(1, accountId);
                ps2.setDouble(2, amount);
                ps2.setString(3, "Cash withdrawal");
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * THE ATOMIC TRANSFER: Moves money between accounts.
     * Success or failure is all-or-nothing.
     */
    public boolean transfer(int fromCustomerId, int toAccountId, double amount) {
        String withdrawSQL = "UPDATE accounts SET balance = balance - ? WHERE customer_id = ? AND balance >= ?";
        String depositSQL = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        String logSQL = "INSERT INTO transactions (account_id, amount, type, reference_account, description) VALUES (?, ?, 'TRANSFER', ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Get Sender's Account ID
            Integer fromAccountId = getAccountIdInternal(conn, fromCustomerId);
            if (fromAccountId == null) return false;

            // 2. Withdraw from Sender
            try (PreparedStatement psWithdraw = conn.prepareStatement(withdrawSQL)) {
                psWithdraw.setDouble(1, amount);
                psWithdraw.setInt(2, fromCustomerId);
                psWithdraw.setDouble(3, amount);
                if (psWithdraw.executeUpdate() == 0) {
                    conn.rollback();
                    return false; // Likely insufficient funds
                }
            }

            // 3. Deposit to Receiver
            try (PreparedStatement psDeposit = conn.prepareStatement(depositSQL)) {
                psDeposit.setDouble(1, amount);
                psDeposit.setInt(2, toAccountId);
                if (psDeposit.executeUpdate() == 0) {
                    conn.rollback();
                    return false; // Receiver account ID doesn't exist
                }
            }

            // 4. Log the Transaction for the Sender
            try (PreparedStatement psLog = conn.prepareStatement(logSQL)) {
                psLog.setInt(1, fromAccountId);
                psLog.setDouble(2, amount);
                psLog.setInt(3, toAccountId);
                psLog.setString(4, "Transfer to Account #" + toAccountId);
                psLog.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}