package com.brainware.simplebankingapp.controller;

import com.brainware.simplebankingapp.StageManager;
import com.brainware.simplebankingapp.model.User;
import com.brainware.simplebankingapp.service.AuthService;
import com.brainware.simplebankingapp.model.UserSession; 
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleLogin() {
        String inputUser = usernameField.getText();
        String inputPass = passwordField.getText();

        // 1. Ask the Service to authenticate
        User loggedInUser = authService.login(inputUser, inputPass);

        if (loggedInUser != null) {
            // 2. SUCCESS: Store the database values in the Session
            // This is critical so the Dashboard knows whose balance to show!
            UserSession.setSession(loggedInUser.getCustomerId(), loggedInUser.getUsername());

            System.out.println("Login successful! Session started for: " + UserSession.getUsername());
            
            // 3. Navigate to the Dashboard
            StageManager.showDashboard(); 
        } else {
            // 4. FAILURE: Show an alert 
            showErrorAlert("Invalid username or password.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}