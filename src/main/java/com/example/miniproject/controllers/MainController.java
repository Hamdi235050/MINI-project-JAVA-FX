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
                menuButtonClasse.setText(label);
                selectedClasseId = id;
            });

            menuButtonClasse.getItems().add(menuItem);
        }
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
                menuButtonDays.setText(day);
                selectedDay = day ;
            });

             menuButtonDays.getItems().add(menuItem);
        }
    }

     @FXML
    private void initializeMenuButtonHours() {
        List<String> hours = new ArrayList<>();
        for (int i = 8; i <= 17; i++) {
            hours.add(String.format("%02d:00", i));
        }
        for (String hour : hours) {
            MenuItem menuItem = new MenuItem(hour);
            menuItem.getStyleClass().add("menuItem");

            menuItem.setOnAction(event -> {
                 menuButtonHours.setText(hour);
                selectedHour= hour;

            });

             menuButtonHours.getItems().add(menuItem);
        }
    }

     private void loadSessionData() {
        ObservableList<Session> sessions = sessionService.loadSessionData();
        sessionTable.setItems(sessions);
    }
    @FXML
    public void initialize() {
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
         String classeId = selectedClasseId ;
        String jour =selectedDay   ;
        String heure = selectedHour;
        String matriculeEnseignants = matriculeEnseignant.getText();
        String matiere = matiereId.getText();
          if (  classeId.isEmpty() || jour.isEmpty() || heure.isEmpty() || matriculeEnseignants.isEmpty() || matiere.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill all the fields for adding a session.");
            return;
        }

         boolean isAdded = sessionService.addSession(   classeId, matiere, jour, heure, matriculeEnseignants );

         if (isAdded) {
            showAlert(Alert.AlertType.INFORMATION, "Session ajoutée avec succès.");
            loadSessionData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec de l'ajout de la session.");
        }
    }
    @FXML
    public void addTeacher() {
        String matricule = getMatriculeText();
        String name = nom.getText();
        String contact = getContactText();
        System.out.print(matricule + name  + contact);
        if ( matricule.isEmpty() || name.isEmpty() || contact.isEmpty()  ) {
            showAlert(Alert.AlertType.ERROR, "Veuillez entrer TOUS LES CHAMPS. ");
        }
        else if (teacherService.addTeacher(matricule, name, contact)) {
            showAlert(Alert.AlertType.INFORMATION, "L'enseignant"+name+ "ajouté avec succès.");
            loadTeacherData(null,null); // Refresh the table data
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec de l'ajout de l'enseignant.");
        }
    }

    private void loadTeacherData(String matriculeFilter, String contactFilter) {
        if (teacherTable == null) {
             return;
        }
        ObservableList<Teacher> teachers = teacherService.loadTeacherData(matriculeFilter, contactFilter);
        teacherTable.setItems(teachers);
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
           alert.setHeaderText("le matricule, le nom ou le contact est vide. ");
       }
        if (teacherService.addTeacher(matricule, name, contact)) {
            showAlert(Alert.AlertType.INFORMATION,  "L'enseignant"+name+ "ajouté avec succès.");
            loadTeacherData(null,null);
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec de l'ajout de l'enseignant.");
        }
    }
    @FXML
    public void onSaveButtonClick() {

        addTeacher();
    }

    @FXML
    private void onModifyButtonClick() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();

         String matricule = selectedTeacher != null ? selectedTeacher.getMatricule() : getMatriculeText().trim();
         if (selectedTeacher == null && matricule.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner un enseignant ou entrer un matricule.");
            return;
        }

         String name = nom.getText().trim();
        String contact = getContactText().trim();

         if (name.isEmpty() || contact.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Les champs nom et contact ne peuvent pas être vides");
            return;
        }

         if (teacherService.updateTeacher(matricule, name, contact)) {
            showAlert(Alert.AlertType.INFORMATION, "Enseignant mis à jour avec succès.");
            loadTeacherData(null, null);
         } else {
            showAlert(Alert.AlertType.ERROR, "Échec de la mise à jour de l'enseignant. Veuillez vérifier les détails et réessayer.");
        }
    }

    @FXML
    private void onDeleteButtonClick() {
         Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
         String matricule = (selectedTeacher != null) ? selectedTeacher.getMatricule() : getMatriculeText().trim();

         if (matricule == null || matricule.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner un enseignant à supprimer ou entrer un matricule.");
            return;
        }

         teacherService.deleteTeacher(matricule);


        loadTeacherData(null,null);
        showAlert(Alert.AlertType.INFORMATION, "Enseignant Avec matricule " + matricule + " est supprimer.");
    }


    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
