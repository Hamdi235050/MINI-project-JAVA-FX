package com.example.miniproject.controllers;

import com.example.miniproject.dataFetch.DataFetch;
import com.example.miniproject.dataFetch.SessionsId;
import com.example.miniproject.model.Schedule;
import com.example.miniproject.model.Teacher;
import com.example.miniproject.services.ScheduleService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Map;

public class Interface2 {
    @FXML
    private Button backButton;
    @FXML
    private TableView<Schedule> sessionTable;
    private String selectedClasseId;
    @FXML
    private TableColumn<Teacher, String> contactColumn;
    @FXML
    private TableColumn<Teacher, String> sessionIdColumn;
    @FXML
    private TableColumn<Teacher, String> subjectColumn;
    @FXML
    private TableColumn<Teacher, String> classeIdColumn;
    @FXML
    private TableColumn<Teacher, String> dayColumn;
    @FXML
    private TableColumn<Teacher, String> hourColumn;
    @FXML
    private TableColumn<Teacher, String> teacherColumn;
    @FXML
    private TextField  subject ;
    @FXML
    private Button classSearch;
    @FXML
    private MenuButton menuButtonClasseSearch;

    private String selectedSessionId ;
    private String selectedClasseIdSearch;
    private ScheduleService scheduleService = new ScheduleService();

    @FXML
    private MenuButton menuButtonClasse;
    @FXML
    private MenuButton menuButtonIds;
    @FXML
    private void goBackToMainScreen() throws IOException {

         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproject/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

         Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private void loadSessionData(String classeId,  String matiereId) {
        ObservableList<Schedule> sessions = scheduleService.loadScheduleData(classeId, matiereId);
        sessionTable.setItems(sessions);
    }

    @FXML
    private void initializeMenuButtonClasse() {
        DataFetch dataFetch = new DataFetch();
        Map<String, String> data = dataFetch.fetchData();
        MenuItem allItem = new MenuItem("All");


        allItem.setOnAction(event -> {
            menuButtonClasse.setText("All");
            selectedClasseId = null;
         });
        menuButtonClasse.getItems().add(allItem);

         for (Map.Entry<String, String> entry : data.entrySet()) {
            String id = entry.getKey();
            String label = entry.getValue();

             final String currentId = id;

            MenuItem menuItem = new MenuItem(label);
             menuItem.getStyleClass().add("menuItem");

             menuItem.setOnAction(event -> {
                menuButtonClasse.setText(label);
                selectedClasseId = currentId;
             });

            menuButtonClasse.getItems().add(menuItem);
        }
    }
    @FXML
    private void initializeMenuButtonClasseSearch() {
        DataFetch dataFetch = new DataFetch();
        Map<String, String> data = dataFetch.fetchData();
        MenuItem allItem = new MenuItem("Tous");
        allItem.getStyleClass().add("menuItem");

        allItem.setOnAction(event -> {
            menuButtonClasseSearch.setText("All");
            selectedClasseIdSearch = null;
         });
        menuButtonClasseSearch.getItems().add(allItem);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String id = entry.getKey();
            String label = entry.getValue();

            final String currentId = id;

            MenuItem menuItem = new MenuItem(label);
            menuItem.getStyleClass().add("menuItem");

            menuItem.setOnAction(event -> {
                menuButtonClasseSearch.setText(label);
                selectedClasseIdSearch = currentId;
            });

            menuButtonClasseSearch.getItems().add(menuItem);
        }
    }
    @FXML
    public void initializeIds() {
        SessionsId sessionsId = new SessionsId();
        for (String value : sessionsId.fetchIds()) {
            MenuItem menuItem = new MenuItem(value);
            menuItem.getStyleClass().add("menuItem");
            menuItem.setOnAction(event -> {
                menuButtonIds.setText(value);
                selectedSessionId = value;
            });

            menuButtonIds.getItems().add(menuItem);
        }
    }
    @FXML
    public void deleteSession() {
        String sessionToDelete = selectedSessionId;

         if (sessionToDelete == null || sessionToDelete.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un ID, s'il vous plaît.");
            alert.showAndWait();  // Display the alert
            return;  // Exit the method if no ID is selected
        }

         boolean isDeleted = scheduleService.deleteSessionById(sessionToDelete);

         if (isDeleted) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Session supprimée avec succès.");
            alert.showAndWait();
            loadSessionData(null, null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Échec de la suppression de la session. Veuillez réessayer.");
            alert.showAndWait();
        }
    }

    @FXML
    public void  handleSearchFilter() {
        String classeId = selectedClasseId;
        String subjects = subject.getText();
        loadSessionData(classeId,subjects);
    }
    @FXML
    public void  handleClassSearchFilter() {
         String classeId = selectedClasseIdSearch;
         loadSessionData(classeId,null);
    }
    @FXML
    public void initialize() {
        sessionIdColumn.setCellValueFactory(new PropertyValueFactory<>("seanceID"));
        classeIdColumn.setCellValueFactory(new PropertyValueFactory<>("NomClasse"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("jour"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadSessionData(null,null);
        initializeMenuButtonClasse();
        initializeMenuButtonClasseSearch();
        initializeIds();
    }
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
