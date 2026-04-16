package model;
import java.util.ArrayList;

class Groupe extends Artiste {
 
    // --- Attributs supplémentaires ---
    private ArrayList<Artiste> membres; 
 
    // --- Constructeur ---
 
    public Groupe(String nom, String biographie) {
        super(nom, biographie); // Appel du constructeur d'Artiste
        this.membres = new ArrayList<>();
    }
 
    // --- Méthodes ---

    public void ajouterMembre(Artiste a) {
        membres.add(a);
    }
    public boolean retirerMembre(Artiste a) {
        return membres.remove(a);
    }
    public boolean estMembre(Artiste a) {
        return membres.contains(a);
    }
    public int getNbMembres() {
        return membres.size();
    }
 
    // --- Getter ---

    public ArrayList<Artiste> getMembres() {
        return new ArrayList<>(membres);
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Groupe : ").append(nom)
          .append(" | Albums : ").append(getNbAlbums())
          .append(" | Membres : ").append(getNbMembres())
          .append("\nBiographie : ").append(biographie)
          .append("\nMembres : ");
        for (int i = 0; i < membres.size(); i++) {
            sb.append(membres.get(i).getNom());
            if (i < membres.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
 
 

