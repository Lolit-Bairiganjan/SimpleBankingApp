package com.brainware.simplebankingapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.brainware.simplebankingapp.dao.DatabaseConnection;

import com.brainware.simplebankingapp.model.Account;

public class AccountDAO {

    public Account getAccountById(int accountId) {

        String query = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getInt("user_id"),
                    rs.getDouble("balance")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}