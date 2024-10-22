package com.example.miniproject.controllers;
import com.example.miniproject.dataFetch.DataFetch;
import com.example.miniproject.model.Teacher;
import com.example.miniproject.services.SessionService;
import com.example.miniproject.services.TeacherService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {
    @FXML
    private Label selectedItemLabel;
    @FXML
    private MenuButton menuButtonClasse;
    @FXML
    private MenuButton menuButtonDays;
    @FXML
    private MenuButton menuButtonHours;
    @FXML
    private TextField matricule;
    @FXML
    private TextField nom;
    @FXML
    private TextField contact;
    @FXML
    private TableView<Teacher> teacherTable;
    @FXML
    private TableColumn<Teacher, String> matriculeColumn;
    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    private TableColumn<Teacher, String> contactColumn;
    @FXML
    private Button Requests;
    private void switchToScene2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproject/interface2.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) Requests.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final TeacherService teacherService = new TeacherService();
    private final SessionService sesisonService = new SessionService();
    public void handleAddSession(String sessionId, String classeId, String matiereId, String jour, String heure, String matriculeEnseignant) {
        boolean isAdded = sesisonService.addSession(sessionId, classeId, matiereId, jour, heure, matriculeEnseignant);

        if (isAdded) {
            // Success handling, e.g., show confirmation or refresh UI
            System.out.println("Session added successfully!");
        } else {
            // Failure handling, e.g., show error
            System.out.println("Failed to add session.");
        }
    }

    @FXML
    private void initializeMenuButtonClasse(){
        DataFetch dataFetch = new DataFetch();
        List<String> data = dataFetch.fetchData();
        for (String item : data) {
            MenuItem menuItem = new MenuItem(item);
            menuItem.setOnAction(event -> {
                handleMenuItemClick(item);
                menuButtonClasse.setText(item); // Set the selected item as the button's text.
            });

              menuButtonClasse.getItems().add(menuItem);
        }
    }
    private void handleMenuItemClick(String selectedItem) {
         System.out.println("Selected: " + selectedItem);
    }
    @FXML
    private void initializeMenuButtonDays() {
         List<String> daysOfWeek = Arrays.asList(
                "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
        );

         for (String day : daysOfWeek) {
            MenuItem menuItem = new MenuItem(day);
            menuItem.setOnAction(event -> {
                handleMenuItemClickDays(day);
                menuButtonDays.setText(day);
            });

              menuButtonDays.getItems().add(menuItem);
        }
    }
    private void handleMenuItemClickDays(String selectedItem) {
        System.out.println("Selected: " + selectedItem);
    }
    @FXML
    private void initializeMenuButtonHours() {
        List<String> hours = new ArrayList<>();
        for (int i = 8; i <= 17; i++) {
            hours.add(String.format("%02d:00", i));
        }

        for (String hour : hours) {
            MenuItem menuItem = new MenuItem(hour);
            menuItem.setOnAction(event -> {
                handleMenuItemClickHours(hour);
                menuButtonHours.setText(hour);
            });

             menuButtonHours.getItems().add(menuItem);
        }
    }

    private void handleMenuItemClickHours(String selectedItem) {
        System.out.println("Selected: " + selectedItem);
    }

    @FXML
    public void initialize() {
        // Configure table columns
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        initializeMenuButtonClasse();
        initializeMenuButtonDays();
        initializeMenuButtonHours();

        Requests.setOnAction(event -> switchToScene2());
        // Load initial teacher data
        loadTeacherData(null, null);
    }
    @FXML
    private void onFilterButtonClick() {

        String matriculeFilter = getMatriculeText();
        String contactFilter = getContactText();
        System.out.println(matricule);

        loadTeacherData(matriculeFilter,contactFilter);
    }

    private String getContactText() {
        return contact.getText();
    }

    private String getMatriculeText() {
        return matricule.getText();
    }

    @FXML
    public void addTeacher() {
        String matricule = getMatriculeText();
        String name = nom.getText();
        String contact = getContactText();

        if ( matricule.isEmpty() || name.isEmpty() || contact.isEmpty()  ) {
            showAlert(Alert.AlertType.ERROR, "Please Enter ALL THE FIELDS ");
        }
        else if (teacherService.addTeacher(matricule, name, contact)) {
            showAlert(Alert.AlertType.INFORMATION, "Teacher added successfully.");
            loadTeacherData(null,null); // Refresh the table data
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to add the teacher.");
        }
    }

    private void loadTeacherData(String matriculeFilter, String contactFilter) {
        if (teacherTable == null) {
            System.out.println("teacherTable is null!");
            return;
        }
        ObservableList<Teacher> teachers = teacherService.loadTeacherData(matriculeFilter, contactFilter);
        teacherTable.setItems(teachers); // Now this should work without a NullPointerException

    }

    @FXML
    private void handleAddTeacher() {
        String matricule = getMatriculeText();
        String name = nom.getText();
        String contact =  getContactText();
       if (matricule.isEmpty() || name.isEmpty() || contact.isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setHeaderText("error matricule or name or contact is emty ");
       }
        if (teacherService.addTeacher(matricule, name, contact)) {
            showAlert(Alert.AlertType.INFORMATION, "Teacher added successfully.");
            loadTeacherData(null,null); // Refresh the table data
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to add the teacher.");
        }
    }
    @FXML
    public void onSaveButtonClick() {

        addTeacher();
    }

    @FXML
    private void onModifyButtonClick() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();

        // Check if a teacher is selected or if matricule field is filled
        String matricule = selectedTeacher != null ? selectedTeacher.getMatricule() : getMatriculeText().trim(); // Assuming you have a TextField for matricule input

        // If both the selected teacher and matricule input are absent, show a warning
        if (selectedTeacher == null && matricule.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a teacher or enter a matricule.");
            return;
        }

        // Get the updated name and contact from the input fields
        String name = nom.getText().trim(); // Get and trim the name input
        String contact = getContactText().trim(); // Get and trim the contact input

        // Validate inputs
        if (name.isEmpty() || contact.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Name and contact fields cannot be empty.");
            return;
        }

        // Check if the matricule exists for an update
        if (teacherService.updateTeacher(matricule, name, contact)) {
            showAlert(Alert.AlertType.INFORMATION, "Teacher updated successfully.");
            loadTeacherData(null, null); // Refresh the table data
         } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update the teacher. Please check the details and try again.");
        }
    }

    @FXML
    private void onDeleteButtonClick() {
        // Attempt to get the selected teacher from the table
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + selectedTeacher.getMatricule());
        String matricule = null;

        // If a teacher is selected, get their matricule
        if (selectedTeacher != null) {
            matricule = selectedTeacher.getMatricule();
        } else {
            // If no teacher is selected, check the matriculeField for input
            matricule = getMatriculeText().trim();
        }

        // Check if matricule is null or empty
        if (matricule == null || matricule.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a teacher to delete or enter a matricule.");
            return;
        }

        // Perform deletion
        if (teacherService.deleteTeacher(matricule)) {
            showAlert(Alert.AlertType.INFORMATION, "Teacher deleted successfully.");
            loadTeacherData(null, null); // Refresh the table data
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to delete the teacher.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
