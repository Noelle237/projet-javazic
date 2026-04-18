package modele;

import java.util.ArrayList;
import java.util.List;

public class Gestionnaire {
    private static Gestionnaire instance;
    private List<Utilisateur> utilisateurs;
    private Catalogue catalogue;

    private Gestionnaire() {
        this.utilisateurs = new ArrayList<>();
        this.catalogue = new Catalogue();
    }

    public static Gestionnaire getInstance() {
        if (instance == null) {
            instance = new Gestionnaire();
        }
        return instance;
    }

    public List<Utilisateur> getUtilisateurs() { return utilisateurs; }
    public Catalogue getCatalogue() { return catalogue; }

    public void ajouterUtilisateur(Utilisateur u) { utilisateurs.add(u); }
    public void supprimerUtilisateur(Utilisateur u) { utilisateurs.remove(u); }

    public Utilisateur authentifier(String identifiant, String motDePasse) {
        return utilisateurs.stream()
            .filter(u -> u.getIdentifiant().equals(identifiant) && u.getMotDePasse().equals(motDePasse))
            .findFirst()
            .orElse(null);
    }
}