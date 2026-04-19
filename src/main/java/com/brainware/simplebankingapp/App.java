package com.brainware.simplebankingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    @Override
    public void start(Stage stage) {
        StageManager.setStage(stage); // Hand over the stage control
        StageManager.showLogin();     // Start with the login screen
    }

    public static void main(String[] args) {
        launch();
    }

}