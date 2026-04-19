package modele;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gestionnaire implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String FICHIER_SAUVEGARDE = 
        System.getProperty("user.home") + "/javazic_data.ser";

    private static Gestionnaire instance;
    private List<Utilisateur> utilisateurs;
    private Catalogue catalogue;

    private Gestionnaire() {
        this.utilisateurs = new ArrayList<>();
        this.catalogue = new Catalogue();
    }

    public static Gestionnaire getInstance() {
        if (instance == null) {
            instance = charger();
        }
        return instance;
    }

    public void sauvegarder() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHIER_SAUVEGARDE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    private static Gestionnaire charger() {
        File fichier = new File(FICHIER_SAUVEGARDE);
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fichier))) {
                return (Gestionnaire) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur chargement : " + e.getMessage());
            }
        }
        return new Gestionnaire();
    }

    public List<Utilisateur> getUtilisateurs() { return utilisateurs; }
    public Catalogue getCatalogue() { return catalogue; }
    public void ajouterUtilisateur(Utilisateur u) { utilisateurs.add(u); }
    public void supprimerUtilisateur(Utilisateur u) { utilisateurs.remove(u); }

    public boolean identifiantExiste(String identifiant) {
        return utilisateurs.stream()
            .anyMatch(u -> u.getIdentifiant().equals(identifiant));
    }

    public Utilisateur trouverUtilisateur(String identifiant) {
        return utilisateurs.stream()
            .filter(u -> u.getIdentifiant().equals(identifiant))
            .findFirst().orElse(null);
    }

    public Utilisateur authentifier(String identifiant, String motDePasse) {
        return utilisateurs.stream()
            .filter(u -> u.getIdentifiant().equals(identifiant)
                      && u.getMotDePasse().equals(motDePasse))
            .findFirst().orElse(null);
    }
}