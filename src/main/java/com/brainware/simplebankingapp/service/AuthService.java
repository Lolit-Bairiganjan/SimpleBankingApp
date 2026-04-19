package com.brainware.simplebankingapp.service;

import com.brainware.simplebankingapp.dao.UserDAO;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public boolean login(String username, String password) {
        // Business logic: check if fields are empty before even hitting the DB
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        // Call the DAO to check the database
        return userDAO.validateUser(username, password);
    }
}