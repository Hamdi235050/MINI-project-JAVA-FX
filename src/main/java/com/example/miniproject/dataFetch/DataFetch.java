package com.example.miniproject.dataFetch;

import com.example.miniproject.model.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import java.util.Map;

public class DataFetch {
    public Map<String, String> fetchData() {
        Map<String, String> data = new HashMap<>();
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connection = dbConnection.connect();

        try {
            String query = "SELECT id, NomClasse FROM CLASSES";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String label = resultSet.getString("NomClasse");
                data.put(id, label); // Use id as the key and NomClasse as the value
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }


}
