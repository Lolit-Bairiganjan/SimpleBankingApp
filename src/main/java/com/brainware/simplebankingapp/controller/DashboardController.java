package com.brainware.simplebankingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML private Label checkingBalanceLabel;
    @FXML private Label savingsBalanceLabel;
    @FXML private TableView<?> transactionTable; // Use your Transaction model here later
    @FXML private StackPane contentArea;
    @FXML private VBox dashboardView;

    @FXML
    public void initialize() {
        // This runs when the FXML loads. 
        // For now, it just confirms the UI is working.
        System.out.println("Dashboard UI Initialized!");
    }

    @FXML
    private void showDashboardView() {
        dashboardView.setVisible(true);
    }

    @FXML
    private void showTransferView() {
        // Logic to swap views will go here tomorrow
        System.out.println("Transfer view requested");
    }

    @FXML
    private void handleLogout() {
        System.out.println("Logging out...");
        System.exit(0); 
    }
}