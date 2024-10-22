package com.example.miniproject;
import com.example.miniproject.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {
    private Scene mainScene;
     @FXML
     private TextField nom ;
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();
            if (root == null) {
                System.out.println("Root is null after loading FXML.");
            } else {
                System.out.println("Root loaded successfully.");
            }

            MainController controller = fxmlLoader.getController();
            System.out.println(controller);
            if (root == null) {
                System.out.println("Failed to load FXML: root is null.");
            } else {
                System.out.println("FXML loaded successfully.");
            }

            mainScene = new Scene(root, 795, 650);
            System.out.println("Scene initialized: " + (mainScene != null));

            stage.setTitle("Gestion des emplois du temps ISI");
            stage.setScene(mainScene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/school.png")));

            mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            try {
                Button saveTeacher = (Button) root.lookup("#save");
                if (saveTeacher != null) {
                    saveTeacher.setOnAction(e -> controller.addTeacher());
                } else {
                    System.out.println("Save button not found!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            stage.show();
            System.out.println("Stage shown successfully.");
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void openNewScene(Stage stage) {
        try {
            // Load the new scene from FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interface2.fxml"));
            Parent newRoot = loader.load(); // Load the new scene


            Scene newScene = new Scene(newRoot, 300, 200); // Create a new scene
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
