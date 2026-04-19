package com.brainware.simplebankingapp.controller;

import com.brainware.simplebankingapp.StageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 1. Check if fields are empty (Basic Validation)
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // 2. Call Member B's authentication logic
        //boolean isAuthenticated = UserService.authenticate(username, password);

//        if (isAuthenticated) {
//            // 3. Success: Transition to the Dashboard [cite: 11]
//            StageManager.showDashboard();
//        } else {
//            // 4. Failure: Show an Alert 
//            showAlert("Login Failed", "Invalid Credentials.");
//        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}