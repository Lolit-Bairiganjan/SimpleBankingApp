module com.brainware.simplebankingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
   
    opens com.brainware.simplebankingapp.controller to javafx.fxml;
    opens com.brainware.simplebankingapp to javafx.fxml;
    exports com.brainware.simplebankingapp;
    exports com.brainware.simplebankingapp.controller;
}
