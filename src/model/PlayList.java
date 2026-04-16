package model;
import java.io.Serializable;
import java.util.ArrayList;

class PlayList implements Serializable {

    // --- Attributs ---
    private String             nom;          
    private ArrayList<Morceau> morceaux;     
    private Abonne             proprietaire; 
    // --- Constructeur ---

    public PlayList(String nom, Abonne proprietaire) {
        this.nom          = nom;
        this.proprietaire = proprietaire;
        this.morceaux     = new ArrayList<>();
    }

    // --- Méthodes ---

    public void ajouter(Morceau m) throws MorceauDejaPresent {
        if (morceaux.contains(m)) {
            throw new MorceauDejaPresent(
                "Le morceau '" + m.getTitre() + "' est déjà dans la playlist '" + nom + "'."
            );
        }
        morceaux.add(m);
    }

    public void retirer(Morceau m) throws MorceauIntrouvable {
        if (!morceaux.remove(m)) {
            throw new MorceauIntrouvable(
                "Le morceau '" + m.getTitre() + "' n'est pas dans la playlist '" + nom + "'."
            );
        }
    }

    public int getDureeTotal() {
        int total = 0;
        for (Morceau m : morceaux) {
            total += m.getDuree();
        }
        return total;
    }

    public String getDureeTotalFormatee() {
        int totalSec = getDureeTotal();
        int minutes  = totalSec / 60;
        int secondes = totalSec % 60;
        return minutes + "min " + secondes + "s";
    }

    public boolean estVide() {
        return morceaux.isEmpty();
    }

    public int getNbMorceaux() {
        return morceaux.size();
    }

    // --- Getters et Setters ---

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Morceau> getMorceaux() {
        return new ArrayList<>(morceaux);
    }

    public Abonne getProprietaire() {
        return proprietaire;
    }

    @Override
    public String toString() {
        return "Playlist '" + nom + "' — " + getNbMorceaux()
                + " morceaux — Durée : " + getDureeTotalFormatee()
                + " (propriétaire : " + proprietaire.getNom() + ")";
    }
}
