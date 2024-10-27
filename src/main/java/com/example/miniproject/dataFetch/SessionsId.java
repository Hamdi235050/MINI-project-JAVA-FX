package com.example.miniproject.dataFetch;

import com.example.miniproject.model.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SessionsId {
    public ArrayList<String> fetchIds() {
       ArrayList<String> ids = new ArrayList<>();
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connection = dbConnection.connect();

        try {
            String query = "SELECT seanceID FROM seances";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("seanceID");

                ids.add(id );
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }
}
