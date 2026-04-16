package model;

import java.io.Serializable;
import java.util.ArrayList;

// Gère la liste de tous les abonnés et des administrateurs du système
// C'est cet objet qui est sérialisé pour persister les comptes utilisateurs
public class GestionnaireUtilisateurs implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Abonne>         abonnes;
    private ArrayList<Administrateur> administrateurs;

    public GestionnaireUtilisateurs() {
        this.abonnes         = new ArrayList<>();
        this.administrateurs = new ArrayList<>();
        // Compte administrateur par défaut créé au premier lancement
        this.administrateurs.add(new Administrateur("admin", "admin1234", "Administrateur"));
    }

    // Inscrit un nouvel abonné (login unique obligatoire)
    public boolean inscrireAbonne(String login, String mdp, String nom) {
        if (trouverAbonne(login) != null) {
            return false;  // login déjà pris
        }
        abonnes.add(new Abonne(login, mdp, nom));
        return true;
    }

    // Recherche un abonné par son login
    public Abonne trouverAbonne(String login) {
        for (Abonne a : abonnes) {
            if (a.getLogin().equals(login)) {
                return a;
            }
        }
        return null;
    }

    // Recherche un administrateur par son login
    public Administrateur trouverAdministrateur(String login) {
        for (Administrateur a : administrateurs) {
            if (a.getLogin().equals(login)) {
                return a;
            }
        }
        return null;
    }

    // Vérifie les identifiants d'un abonné et retourne l'objet si valide
    public Abonne connecterAbonne(String login, String mdp) {
        Abonne a = trouverAbonne(login);
        if (a != null && a.verifierIdentifiants(login, mdp)) {
            return a;
        }
        return null;
    }

    // Vérifie les identifiants d'un administrateur et retourne l'objet si valide
    public Administrateur connecterAdministrateur(String login, String mdp) {
        Administrateur a = trouverAdministrateur(login);
        if (a != null && a.verifierIdentifiants(login, mdp)) {
            return a;
        }
        return null;
    }

    public ArrayList<Abonne> getAbonnes() {
        return abonnes;
    }

    public ArrayList<Administrateur> getAdministrateurs() {
        return administrateurs;
    }
}