package com.brainware.simplebankingapp.service;

import com.brainware.simplebankingapp.dao.AccountDAO;

public class AccountService {

    private final AccountDAO accountDAO = new AccountDAO();

    /**
     * Fetches the current balance for the logged-in customer.
     * Bridges the UI to the 'getBalanceByCustomerId' method in the DAO.
     */
    public double getBalance(int customerId) {
        if (customerId <= 0) return 0.0;

        // Using Double (capital D) to handle potential nulls from the DAO safely
        Double balance = accountDAO.getBalanceByCustomerId(customerId);
        
        return (balance != null) ? balance : 0.0;
    }

    /**
     * Processes a deposit request from the UI.
     */
    public boolean deposit(int customerId, double amount) {
        if (amount <= 0) return false;
        return accountDAO.deposit(customerId, amount);
    }

    /**
     * Processes a withdrawal request from the UI.
     */
    public boolean withdraw(int customerId, double amount) {
        if (amount <= 0) return false;
        return accountDAO.withdraw(customerId, amount);
    }

    /**
     * THE CORE INTEGRATION: Bridges the Transfer UI to the Atomic Transfer logic.
     * This is the most critical method for your Minimum Viable Product (MVP).
     */
    public boolean transfer(int fromCustomerId, int toAccountId, double amount) {
        // Basic validation before hitting the database
        if (amount <= 0 || fromCustomerId <= 0 || toAccountId <= 0) {
            return false;
        }
        return accountDAO.transfer(fromCustomerId, toAccountId, amount);
    }
}