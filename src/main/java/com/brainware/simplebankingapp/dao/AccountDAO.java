package com.brainware.simplebankingapp.dao;

import com.brainware.simplebankingapp.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

    public Double getBalanceByCustomerId(int customerId) {
        String query = "SELECT balance FROM accounts WHERE customer_id = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer getAccountIdByCustomerId(int customerId) {
        String query = "SELECT account_id FROM accounts WHERE customer_id = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("account_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean deposit(int customerId, double amount) {
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE customer_id = ?";
        String insertQuery = "INSERT INTO transactions (account_id, amount, type, description) VALUES (?, ?, 'DEPOSIT', ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(updateQuery);
            ps1.setDouble(1, amount);
            ps1.setInt(2, customerId);
            int updated = ps1.executeUpdate();

            if (updated == 0) {
                conn.rollback();
                return false;
            }

            Integer accountId = getAccountIdByCustomerId(customerId);
            if (accountId == null) {
                conn.rollback();
                return false;
            }

            PreparedStatement ps2 = conn.prepareStatement(insertQuery);
            ps2.setInt(1, accountId);
            ps2.setDouble(2, amount);
            ps2.setString(3, "Cash deposit");
            ps2.executeUpdate();

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean withdraw(int customerId, double amount) {
        String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE customer_id = ? AND balance >= ?";
        String insertQuery = "INSERT INTO transactions (account_id, amount, type, description) VALUES (?, ?, 'WITHDRAW', ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(updateQuery);
            ps1.setDouble(1, amount);
            ps1.setInt(2, customerId);
            ps1.setDouble(3, amount);
            int updated = ps1.executeUpdate();

            if (updated == 0) {
                conn.rollback();
                return false;
            }

            Integer accountId = getAccountIdByCustomerId(customerId);
            if (accountId == null) {
                conn.rollback();
                return false;
            }

            PreparedStatement ps2 = conn.prepareStatement(insertQuery);
            ps2.setInt(1, accountId);
            ps2.setDouble(2, amount);
            ps2.setString(3, "Cash withdrawal");
            ps2.executeUpdate();

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}