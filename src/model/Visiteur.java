package model;
import java.util.ArrayList;

class Visiteur extends Utilisateur {

    public static final int MAX_ECOUTES_VISITEUR = 5;
    private int nbEcoutesDansSession;

    // --- Constructeur ---

    public Visiteur() {
        super("visiteur", "", "Visiteur anonyme");
        this.nbEcoutesDansSession = 0;
    }

    // --- Méthodes ---
    public boolean peutEcouter() {
        return nbEcoutesDansSession < MAX_ECOUTES_VISITEUR;
    }

    public void incrementerEcoutes() {
        if (peutEcouter()) {
            nbEcoutesDansSession++;
        }
    }

    public int getEcoutesRestantes() {
        return MAX_ECOUTES_VISITEUR - nbEcoutesDansSession;
    }

    public int getNbEcoutesDansSession() {
        return nbEcoutesDansSession;
    }

    public void reinitialiserSession() {
        nbEcoutesDansSession = 0;
    }

    @Override
    public String getRole() {
        return "Visiteur";
    }

    @Override
    public String toString() {
        return "[Visiteur] Écoutes restantes cette session : "
                + getEcoutesRestantes() + "/" + MAX_ECOUTES_VISITEUR;
    }
}

