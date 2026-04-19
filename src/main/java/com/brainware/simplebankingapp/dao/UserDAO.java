package com.brainware.simplebankingapp.dao;

import com.brainware.simplebankingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCustomerId((Integer) rs.getObject("customer_id"));
                user.setAdminId((Integer) rs.getObject("admin_id"));

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
