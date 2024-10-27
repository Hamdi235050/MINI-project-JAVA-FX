package com.example.miniproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Session {
    private final SimpleStringProperty seanceID;
    private final SimpleStringProperty heure;
    private final SimpleStringProperty jour;
    private final SimpleStringProperty nomClasse;
    private final SimpleStringProperty nomMatiere;
    private final SimpleStringProperty matriculeEnseignant;

    // Constructor
    public Session(String seanceID, String nomClasse, String nomMatiere, String jour, String heure, String matriculeEnseignant) {
        this.seanceID = new SimpleStringProperty(seanceID);
        this.heure = new SimpleStringProperty(heure);
        this.jour = new SimpleStringProperty(jour);
        this.nomClasse = new SimpleStringProperty(nomClasse);
        this.nomMatiere = new SimpleStringProperty(nomMatiere);
        this.matriculeEnseignant = new SimpleStringProperty(matriculeEnseignant);
    }

    // Getter for seanceID
    public String getSeanceID() {
        return seanceID.get();
    }

    // Getter for heure
    public String getHeure() {
        return heure.get();
    }

    // Getter for jour
    public String getJour() {
        return jour.get();
    }

    // Getter for classId
    public String getClassId() {
        return nomClasse.get();
    }

    // Getter for nomMatiere
    public String getNomMatiere() {
        return nomMatiere.get();
    }

    // Getter for matriculeEnseignant
    public String getMatriculeEnseignant() {
        return matriculeEnseignant.get();
    }

    // Property getters (for JavaFX bindings)
    public SimpleStringProperty seanceIDProperty() {
        return seanceID;
    }

    public SimpleStringProperty heureProperty() {
        return heure;
    }

    public SimpleStringProperty jourProperty() {
        return jour;
    }

    public SimpleStringProperty classIdProperty() {
        return nomClasse;
    }

    public SimpleStringProperty nomMatiereProperty() {
        return nomMatiere;
    }

    public SimpleStringProperty matriculeEnseignantProperty() {
        return matriculeEnseignant;
    }
}
