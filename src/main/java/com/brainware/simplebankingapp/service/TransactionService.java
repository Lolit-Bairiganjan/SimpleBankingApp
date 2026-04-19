//package com.brainware.simplebankingapp.service;
//
//import com.brainware.simplebankingapp.dao.AccountDAO;
//import com.brainware.simplebankingapp.dao.TransactionDAO;
//import com.brainware.simplebankingapp.dao.DatabaseConnection;
//import com.brainware.simplebankingapp.model.Account;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class TransactionService {
//
//    private AccountDAO accountDAO = new AccountDAO();
//    private TransactionDAO transactionDAO = new TransactionDAO();
//
//    public boolean transfer(int fromId, int toId, double amount) {
//
//        if (amount <= 0 || fromId == toId)
//            return false;
//
//        try (Connection conn = DatabaseConnection.getConnection()) {
//
//            conn.setAutoCommit(false);
//
//            Account from = accountDAO.getAccountById(fromId);
//            Account to = accountDAO.getAccountById(toId);
//
//            if (from == null || to == null || from.getBalance() < amount) {
//                conn.rollback();
//                return false;
//            }
//
//            from.setBalance(from.getBalance() - amount);
//            to.setBalance(to.getBalance() + amount);
//
//            accountDAO.updateAccount(from);
//            accountDAO.updateAccount(to);
//
//            transactionDAO.addTransaction(fromId, toId, amount, "TRANSFER");
//
//            conn.commit();
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}