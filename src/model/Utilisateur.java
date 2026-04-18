package model;

import java.io.Serializable;
import java.util.ArrayList;


abstract class Utilisateur implements Serializable {

    // --- Attributs ---
    protected String login;      
    protected String motDePasse;  
    protected String nom;        

    // --- Constructeur ---

    public Utilisateur(String login, String motDePasse, String nom) {
        this.login      = login;
        this.motDePasse = motDePasse;
        this.nom        = nom;
    }

    // --- Méthodes ---

    public boolean seConnecter(String login, String motDePasse) {
        return this.login.equals(login) && this.motDePasse.equals(motDePasse);
    }

    public abstract String getRole();

    // --- Getters et Setters ---

    public String getLogin() {
        return login;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "[" + getRole() + "] " + nom + " (login: " + login + ")";
    }
}
