package com.brainware.simplebankingapp.service;

import com.brainware.simplebankingapp.dao.UserDAO;
import com.brainware.simplebankingapp.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        // Validation check
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        
        // Return the full User object from the DAO instead of just 'true'
        return userDAO.login(username, password);
    }
}