package com.brainware.simplebankingapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class StageManager {
    private static Stage primaryStage;

    // Call this once in your App.java start() method
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void showLogin() {
        loadScene("/com/brainware/simplebankingapp/view/Login.fxml", "Maze Bank - Login");
    }

    public static void showDashboard() {
        loadScene("/com/brainware/simplebankingapp/view/Dashboard.fxml", "Maze Bank - Dashboard");
    }

    private static void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(StageManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}