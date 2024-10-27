package com.example.miniproject.controllers;
import com.example.miniproject.dataFetch.DataFetch;
import com.example.miniproject.model.Session;
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
import java.util.*;

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
    private String selectedClasseId;

    @FXML
    private TableView<Teacher> teacherTable;
    @FXML
    private TableColumn<Teacher, String> matriculeColumn;
    @FXML
    private TextField matriculeEnseignant;
    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    private TableColumn<Teacher, String> contactColumn;
    @FXML
    private Button Requests;
    @FXML
    private TableView<Session>  sessionTable;

    @FXML
    private TableColumn<Session, String> classeIdColumn;
    private String selectedDay ;
    private String selectedHour;

    @FXML
    private TableColumn<Session, String> matiereIdColumn;
    @FXML
    private TextField matiereId;
    @FXML
    private TableColumn<Session, String> jourColumn;
    @FXML
    private TableColumn<Session, String> heureColumn;
    @FXML
    private TableColumn<Session, String> ensIdColumn;

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
    private final SessionService sessionService = new SessionService();
    public void handleAddSession(String sessionId, String classeId, String matiereId, String jour, String heure, String matriculeEnseignant) {
        boolean isAdded = sessionService.addSession( classeId, matiereId, jour, heure, matriculeEnseignant);

        if (isAdded) {
             System.out.println("Session added successfully!");
        } else {
             System.out.println("Failed to add session.");
        }
    }

    @FXML
    private void initializeMenuButtonClasse() {
        DataFetch dataFetch = new DataFetch();
        Map<String, String> data = dataFetch.fetchData();

         for (Map.Entry<String, String> entry : data.entrySet()) {
            String id = entry.getKey();
            String label = entry.getValue();


            MenuItem menuItem = new MenuItem(label);
             menuItem.getStyleClass().add("menuItem");

             menuItem.setOnAction(event -> {
                handleMenuItemClick(id);
                menuButtonClasse.setText(label);
                selectedClasseId = id;
            });

            menuButtonClasse.getItems().add(menuItem);
        }
    }
    private void handleMenuItemClick(String selectedItem) {
         System.out.println("Selected: " + selectedItem);
    }
    @FXML
    private void initializeMenuButtonDays() {
        Map<Integer, String> daysOfWeek = new HashMap<>();
        daysOfWeek.put(1, "Lundi");
        daysOfWeek.put(2, "Mardi");
        daysOfWeek.put(3, "Mercredi");
        daysOfWeek.put(4, "Jeudi");
        daysOfWeek.put(5, "Vendredi");
        daysOfWeek.put(6, "Samedi");
        daysOfWeek.put(7, "Dimanche");

         for (Map.Entry<Integer, String> entry : daysOfWeek.entrySet()) {
            Integer key = entry.getKey();
            String day = entry.getValue();

            MenuItem menuItem = new MenuItem(day);
             menuItem.getStyleClass().add("menuItem");

             menuItem.setOnAction(event -> {
                handleMenuItemClickDays(key);
                menuButtonDays.setText(day);
                selectedDay = day ;
            });

             menuButtonDays.getItems().add(menuItem); // Add the menu item to the menu button
        }
    }

    private void handleMenuItemClickDays(Integer selectedItem) {
        System.out.println("Selected: " + selectedItem);
    }
    @FXML
    private void initializeMenuButtonHours() {
        List<String> hours = new ArrayList<>();
        for (int i = 8; i <= 17; i++) {
            hours.add(String.format("%02d:00", i));
        }
        selectedHour= hours.get(0);
        for (String hour : hours) {
            MenuItem menuItem = new MenuItem(hour);
            menuItem.getStyleClass().add("menuItem");

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
    private void loadSessionData() {
        ObservableList<Session> sessions = sessionService.loadSessionData();
        sessionTable.setItems(sessions); // Load the data into the table
    }
    @FXML
    public void initialize() {
        // Configure table columns
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        classeIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        matiereIdColumn.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        jourColumn.setCellValueFactory(new PropertyValueFactory<>("jour"));
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        ensIdColumn.setCellValueFactory(new PropertyValueFactory<>("matriculeEnseignant"));

        initializeMenuButtonClasse();
        initializeMenuButtonDays();
        initializeMenuButtonHours();

        Requests.setOnAction(event -> switchToScene2());
        // Load initial teacher data
        loadTeacherData(null, null);
       loadSessionData();
    }
    @FXML
    private void onFilterButtonClick() {
        String matriculeFilter = getMatriculeText();
        String contactFilter = getContactText();

        loadTeacherData(matriculeFilter,contactFilter);
    }

    private String getContactText() {
        return contact.getText();
    }

    private String getMatriculeText() {
        return matricule.getText();
    }
    @FXML
    private void onAddSessionButtonClick() {
         String classeId = selectedClasseId ; // Assuming the menu button's text holds the selected class
        String jour =selectedDay   ; // Assuming the menu button's text holds the selected day
        String heure = selectedHour; // Assuming the menu button's text holds the selected hour
        String matriculeEnseignants = matriculeEnseignant.getText(); // Teacher matricule
        String matiere = matiereId.getText(); // Subject ID
       System.out.println(selectedClasseId+ ' ' + selectedDay +' ' + selectedHour+ ' ' + matriculeEnseignant+ ' ' + matiereId) ;
        // Validate inputs
        if (  classeId.isEmpty() || jour.isEmpty() || heure.isEmpty() || matriculeEnseignants.isEmpty() || matiere.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill all the fields for adding a session.");
            return;
        }

         boolean isAdded = sessionService.addSession(   classeId, matiere, jour, heure, matriculeEnseignants );

         if (isAdded) {
            showAlert(Alert.AlertType.INFORMATION, "Session added successfully.");
            loadSessionData(); // Refresh the session table data
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to add session.");
        }
    }
    @FXML
    public void addTeacher() {
        String matricule = getMatriculeText();
        String name = nom.getText();
        String contact = getContactText();
        System.out.print(matricule + name  + contact);
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
