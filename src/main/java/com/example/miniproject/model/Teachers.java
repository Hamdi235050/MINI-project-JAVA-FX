package com.example.miniproject.model;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Teachers {
    @FXML
    private TextField matricule;

    @FXML
    private TextField nom;

    @FXML
    private TextField contact;

    public void addTeacher() {
         if (this.matricule == null || this.nom == null || this.contact == null) {
            System.out.println("One or more TextField references are null. Please check your FXML file.");
            return; // Exit the method early
        }

         String matriculeText = matricule.getText();
        String nomText = nom.getText();
        String contactText = contact.getText();

         if (matriculeText.isEmpty() || nomText.isEmpty() || contactText.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        String query = "INSERT INTO enseignants (matricule, nom, contact) VALUES (?, ?, ?)";

        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, matriculeText);
            preparedStatement.setString(2, nomText);
            preparedStatement.setString(3, contactText);

             int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A new Teacher was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
