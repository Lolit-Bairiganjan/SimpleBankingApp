package com.brainware.simplebankingapp.dao;

import com.brainware.simplebankingapp.model.User;

public class TestUserDAO {

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User user = userDAO.login("admin", "admin123");

        if (user != null) {
            System.out.println("Login successful: " 
                + user.getUsername() + " (" + user.getRole() + ")");
        } else {
            System.out.println("Invalid username or password");
        }
    }
}