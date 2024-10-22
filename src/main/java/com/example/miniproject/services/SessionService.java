package com.example.miniproject.services;

import com.example.miniproject.model.DataBaseConnection;
import com.example.miniproject.model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionService {
    public ObservableList<Session> loadSessionData( ) {
        ObservableList<Session> sessionList = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder("SELECT sessionID, ClasseId, MatiereID, jour, heure, matriculeEnseignant FROM seances");
        List<String> parameters = new ArrayList<>();



        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Session session = new Session(
                            resultSet.getString("session_id"),
                            resultSet.getString("classe"),
                            resultSet.getString("hours"),
                            resultSet.getString("days"),
                            resultSet.getString("matriculeEns"),
                            resultSet.getString("subject")
                            );
                    sessionList.add(session);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessionList;
    }

    public boolean deleteSession(String sessionId) {
        String query = "DELETE FROM sessions WHERE session_id = ?";
        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sessionId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSession(String session, String classe, String subject, String day, String hour, String matriculeEnseignant) {
        String query = "UPDATE seances s " +
                "JOIN classes c ON s.ClasseId = c.ClasseId " +
                "JOIN matiere m ON s.MatiereID = m.MatiereID " +
                "SET s.jour = ?, s.heure = ?, s.matriculeEnseignant = ? " +
                "WHERE s.sessionID = ? AND c.ClasseId = ? AND m.MatiereID = ?";

        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, day);
            preparedStatement.setString(2, hour);
            preparedStatement.setString(3, matriculeEnseignant);
            preparedStatement.setString(4, session);
            preparedStatement.setString(5, classe);
            preparedStatement.setString(6, subject);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public boolean addSession(String sessionId, String classeId, String matiereId, String jour, String heure, String matriculeEnseignant) {
        String query = "INSERT INTO seances (sessionID, ClasseId, MatiereID, jour, heure, matriculeEnseignant) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sessionId);
            preparedStatement.setString(2, classeId);
            preparedStatement.setString(3, matiereId);
            preparedStatement.setString(4, jour);
            preparedStatement.setString(5, heure);
            preparedStatement.setString(6, matriculeEnseignant);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
