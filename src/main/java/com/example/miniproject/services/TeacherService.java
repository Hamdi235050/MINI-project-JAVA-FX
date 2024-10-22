package com.example.miniproject.services;

import com.example.miniproject.model.DataBaseConnection;
import com.example.miniproject.model.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    public ObservableList<Teacher> loadTeacherData(String matriculeFilter, String contactFilter) {
        ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder("SELECT matricule, nom, contact FROM enseignants");
        List<String> parameters = new ArrayList<>();

        if (matriculeFilter != null && !matriculeFilter.trim().isEmpty()) {
            query.append(" WHERE matricule = ?");
            parameters.add(matriculeFilter);
        }

        if (contactFilter != null && !contactFilter.trim().isEmpty()) {
            query.append(parameters.isEmpty() ? " WHERE" : " AND").append(" contact = ?");
            parameters.add(contactFilter);
        }

        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Teacher teacher = new Teacher(
                            resultSet.getString("matricule"),
                            resultSet.getString("nom"),
                            resultSet.getString("contact")
                    );
                    teacherList.add(teacher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherList;
    }

    public boolean deleteTeacher(String matricule) {
        String query = "DELETE FROM enseignants WHERE matricule = ?";
        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, matricule);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTeacher(String matricule, String name, String contact) {
        String query = "UPDATE enseignants SET nom = ?, contact = ? WHERE matricule = ?";
        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contact);
            preparedStatement.setString(3, matricule);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addTeacher(String matricule, String name, String contact) {
        String query = "INSERT INTO enseignants (matricule, nom, contact) VALUES (?, ?, ?)";
        try (Connection connection = DataBaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, matricule);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, contact);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
