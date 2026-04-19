package com.brainware.simplebankingapp.service;

import com.brainware.simplebankingapp.dao.UserDAO;
import com.brainware.simplebankingapp.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public boolean login(String username, String password) {
        // Business logic check
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        
        // Match the method name 'login' from your UserDAO
        User user = userDAO.login(username, password);
        
        // Return true if a user was found in the database
        return user != null; 
    }
}