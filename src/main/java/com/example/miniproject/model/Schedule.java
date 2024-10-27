package com.example.miniproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Schedule {

    private final SimpleStringProperty seanceID;
    private final SimpleStringProperty heure;
    private final SimpleStringProperty jour;
    private final SimpleStringProperty nomClasse;
    private final SimpleStringProperty nomMatiere;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty contact;

    public Schedule(String seanceID, String heure, String jour, String nomClasse, String nomMatiere, String nom, String contact) {
        this.seanceID = new SimpleStringProperty(seanceID) ;
        this.heure = new SimpleStringProperty( heure);
        this.jour = new SimpleStringProperty(jour);
        this.nomClasse =new SimpleStringProperty( nomClasse);
        this.nomMatiere = new SimpleStringProperty(nomMatiere);
        this.nom =new SimpleStringProperty( nom);
        this.contact =new SimpleStringProperty( contact);
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public String getContact() {
        return contact.get();
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public String getNomMatiere() {
        return nomMatiere.get();
    }

    public SimpleStringProperty nomMatiereProperty() {
        return nomMatiere;
    }

    public String getNomClasse() {
        return nomClasse.get();
    }

    public SimpleStringProperty nomClasseProperty() {
        return nomClasse;
    }

    public String getJour() {
        return jour.get();
    }

    public SimpleStringProperty jourProperty() {
        return jour;
    }

    public String getHeure() {
        return heure.get();
    }

    public SimpleStringProperty heureProperty() {
        return heure;
    }

    public String getSeanceID() {
        return seanceID.get();
    }

    public SimpleStringProperty seanceIDProperty() {
        return seanceID;
    }
}
