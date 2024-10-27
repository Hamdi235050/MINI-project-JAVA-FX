package com.example.miniproject.services;

import com.example.miniproject.model.DataBaseConnection;
import com.example.miniproject.model.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService {
    public ObservableList<Schedule> loadScheduleData(String classeId, String matiereId) {
        ObservableList<Schedule> sessionList = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder("SELECT \n" +
                "    se.SeanceID, \n" +
                "    se.ClasseID, \n" +
                "    c.NomClasse, \n" +
                "    se.matiereID, \n" +
                "    se.jour, \n" +
                "    se.heure, \n" +
                "    ens.nom, \n" +  // Added comma here
                "    ens.contact \n" +
                "FROM \n" +
                "    seances se \n" +
                "JOIN \n" +
                "    Classes c ON se.ClasseID = c.ClasseID \n" +
                "JOIN \n" +
                "    enseignants ens ON ens.matricule = se.matriculeEnseignant \n"
        );
        List<String> parameters = new ArrayList<>();

        if (classeId != null && !classeId.isEmpty()) {
            query.append("WHERE se.ClasseID = ? ");
            parameters.add(classeId);
        }

        if (matiereId != null && !matiereId.isEmpty()) {
            if (!parameters.isEmpty()) {
                query.append("AND se.matiereID = ? ");
            } else {
                query.append("WHERE se.matiereID = ? ");
            }
            parameters.add(matiereId);
        }

        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Schedule schedule = new Schedule(
                            resultSet.getString("seanceID"),
                            resultSet.getString("heure"),
                            resultSet.getString("jour"),
                            resultSet.getString("nomClasse"),
                            resultSet.getString("MatiereId"),
                            resultSet.getString("nom"),
                            resultSet.getString("contact")
                    );

                    sessionList.add(schedule);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessionList;
    }
    public boolean deleteSessionById(String sessionId) {
        String query = "DELETE FROM seances WHERE SeanceID = ?";  // Adjust table and column names as necessary

        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sessionId);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;  // Return true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if an error occurred
        }
    }
}