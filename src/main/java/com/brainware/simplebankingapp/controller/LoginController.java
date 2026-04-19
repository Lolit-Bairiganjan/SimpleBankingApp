package com.brainware.simplebankingapp.controller;

import com.brainware.simplebankingapp.StageManager;
import com.brainware.simplebankingapp.service.AuthService;
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
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (authService.login(user, pass)) {
            // Success: Use StageManager to switch to Dashboard 
            System.out.println("Login successful!");
            StageManager.showDashboard(); 
        } else {
            // Failure: Show an alert 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }
}
