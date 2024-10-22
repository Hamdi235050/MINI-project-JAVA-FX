package com.example.miniproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class Interface2 {
    @FXML
    private Button backButton;

    @FXML
    private void goBackToMainScreen() throws IOException {
        System.out.println("Navigating back to main screen...");

         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproject/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

         Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
