package com.example.miniproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Teacher {
    private final SimpleStringProperty matricule;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty contact;

    public Teacher(String matricule, String nom, String contact) {
        this.matricule = new SimpleStringProperty(matricule);
        this.nom = new SimpleStringProperty(nom);
        this.contact = new SimpleStringProperty(contact);
    }

    public String getMatricule() {
        return matricule.get();
    }

    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
    }

    public String getName() {
        return nom.get();
    }

    public void setName(String name) {
        this.nom.set(name);
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }
}