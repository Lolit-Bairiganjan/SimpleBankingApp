package com.brainware.simplebankingapp.controller;

import com.brainware.simplebankingapp.dao.AccountDAO;
import com.brainware.simplebankingapp.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private TextField amountField;

    @FXML
    private Label adminSectionLabel;

    @FXML
    private Button viewUsersButton;

    @FXML
    private Button manageAccountsButton;

    @FXML
    private Button viewTransactionsButton;

    @FXML
    private Label customerSectionLabel;

    @FXML
    private Button viewBalanceButton;

    @FXML
    private Button depositButton;

    @FXML
    private Button withdrawButton;

    private User user;
    private final AccountDAO accountDAO = new AccountDAO();

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            showAdmin();
        } else {
            showCustomer();
        }
    }

    private void showAdmin() {
        adminSectionLabel.setVisible(true);
        viewUsersButton.setVisible(true);
        manageAccountsButton.setVisible(true);
        viewTransactionsButton.setVisible(true);

        customerSectionLabel.setVisible(false);
        viewBalanceButton.setVisible(false);
        depositButton.setVisible(false);
        withdrawButton.setVisible(false);
        amountField.setVisible(false);
        balanceLabel.setVisible(false);
    }

    private void showCustomer() {
        customerSectionLabel.setVisible(true);
        viewBalanceButton.setVisible(true);
        depositButton.setVisible(true);
        withdrawButton.setVisible(true);
        amountField.setVisible(true);
        balanceLabel.setVisible(true);

        adminSectionLabel.setVisible(false);
        viewUsersButton.setVisible(false);
        manageAccountsButton.setVisible(false);
        viewTransactionsButton.setVisible(false);
    }

    @FXML
    private void handleViewBalance() {
        if (user != null && user.getCustomerId() != null) {
            Double balance = accountDAO.getBalanceByCustomerId(user.getCustomerId());

            if (balance != null) {
                balanceLabel.setText("Balance: ₹" + balance);
            } else {
                balanceLabel.setText("No account found");
            }
        } else {
            balanceLabel.setText("Customer account not linked");
        }
    }

    @FXML
    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                balanceLabel.setText("Enter valid amount");
                return;
            }

            if (user == null || user.getCustomerId() == null) {
                balanceLabel.setText("Customer account not linked");
                return;
            }

            boolean success = accountDAO.deposit(user.getCustomerId(), amount);

            if (success) {
                Double newBalance = accountDAO.getBalanceByCustomerId(user.getCustomerId());
                balanceLabel.setText("Deposited! New Balance: ₹" + newBalance);
                amountField.clear();
            } else {
                balanceLabel.setText("Deposit failed");
            }

        } catch (NumberFormatException e) {
            balanceLabel.setText("Invalid input");
        } catch (Exception e) {
            e.printStackTrace();
            balanceLabel.setText("Deposit error");
        }
    }

    @FXML
    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                balanceLabel.setText("Enter valid amount");
                return;
            }

            if (user == null || user.getCustomerId() == null) {
                balanceLabel.setText("Customer account not linked");
                return;
            }

            boolean success = accountDAO.withdraw(user.getCustomerId(), amount);

            if (success) {
                Double newBalance = accountDAO.getBalanceByCustomerId(user.getCustomerId());
                balanceLabel.setText("Withdrawn! New Balance: ₹" + newBalance);
                amountField.clear();
            } else {
                balanceLabel.setText("Insufficient balance or withdraw failed");
            }

        } catch (NumberFormatException e) {
            balanceLabel.setText("Invalid input");
        } catch (Exception e) {
            e.printStackTrace();
            balanceLabel.setText("Withdraw error");
        }
    }
}