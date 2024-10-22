module com.example.miniproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;
    opens com.example.miniproject.model to javafx.base;
    opens com.example.miniproject.dataFetch to javafx.base;


    opens com.example.miniproject to javafx.fxml;
    exports com.example.miniproject;
    exports com.example.miniproject.controllers;
    opens com.example.miniproject.controllers to javafx.fxml;
}