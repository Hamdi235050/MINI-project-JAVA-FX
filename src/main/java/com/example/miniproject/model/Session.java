package com.example.miniproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Session {
    private final SimpleStringProperty session_id;
    private final SimpleStringProperty hours;
    private final SimpleStringProperty days;
    private final SimpleStringProperty classeID;
    private final SimpleStringProperty subjectID;
    private final SimpleStringProperty  matriculeEns;

    public Session(String sessionId, String hours, String days, String classeID, String subjectID, String matriculeEns) {
        this.session_id = new SimpleStringProperty(sessionId) ;
        this.hours = new SimpleStringProperty( hours);
        this.days = new SimpleStringProperty( days);
        this.classeID =  new SimpleStringProperty(classeID);
        this.subjectID =new SimpleStringProperty(subjectID);
        this.matriculeEns =new SimpleStringProperty(matriculeEns);
    }

    public String getHours() {
        return hours.get();
    }

    public String getDays() {
        return days.get();
    }


    public String getClassId() {
        return classeID.get();
    }

    public SimpleStringProperty classIdProperty() {
        return classeID;
    }

    public String getSubjectId() {
        return subjectID.get();
    }

    public SimpleStringProperty subjectIdProperty() {
        return subjectID;
    }

    public String getSession_id() {
        return session_id.get();
    }

    public SimpleStringProperty session_idProperty() {
        return session_id;
    }

    public String getMatriculeEns() {
        return matriculeEns.get();
    }

    public SimpleStringProperty matriculeEnsProperty() {
        return matriculeEns;
    }

    public String getClasseID() {
        return classeID.get();
    }

    public SimpleStringProperty classeIDProperty() {
        return classeID;
    }

    public String getSubjectID() {
        return subjectID.get();
    }

    public SimpleStringProperty subjectIDProperty() {
        return subjectID;
    }
}
