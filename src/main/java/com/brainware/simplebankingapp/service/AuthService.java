package com.brainware.simplebankingapp.service;

import com.brainware.simplebankingapp.dao.UserDAO;
import com.brainware.simplebankingapp.model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public boolean login(String username, String password) {

        if (username == null || password == null)
            return false;

        User user = userDAO.getUserByUsername(username);

        if (user == null)
            return false;

        return user.getPassword().equals(password);
    }
}