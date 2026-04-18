package controller;

import modele.Abonne;
import modele.Gestionnaire;
import modele.Catalogue;

// Singleton pour partager les données entre controllers JavaFX
public class AppContext {

    private static AppContext instance;

    private GestionnaireUtilisateurs gestionnaire;
    private Catalogue catalogue;
    private Abonne abonneConnecte;
    private String role;

    private AppContext() {
        this.gestionnaire = new Gestionnaire();
        this.catalogue    = new Catalogue();
    }

    public static AppContext getInstance() {
        if (instance == null) instance = new AppContext();
        return instance;
    }

    public GestionnaireUtilisateurs getGestionnaire() { return gestionnaire; }
    public Catalogue getCatalogue() { return catalogue; }

    public Abonne getAbonneConnecte() { return abonneConnecte; }
    public void setAbonneConnecte(Abonne a) { this.abonneConnecte = a; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}