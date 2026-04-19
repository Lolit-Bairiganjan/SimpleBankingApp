package com.brainware.simplebankingapp.service;

import com.brainware.simplebankingapp.dao.AccountDAO;
import com.brainware.simplebankingapp.model.Account;

public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();

    public double getBalance(int accountId) {

        if (accountId <= 0) return 0;

        Account acc = accountDAO.getAccountById(accountId);

        if (acc == null) return 0;

        return acc.getBalance();
    }
}