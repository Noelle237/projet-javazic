package model;
import java.io.Serializable;
import java.util.ArrayList;

class Artiste implements Serializable {
 
    // --- Attributs ---
    protected String          nom;         
    protected String          biographie;  
    protected ArrayList<Album> albums;   
    // --- Constructeur ---

    public Artiste(String nom, String biographie) {
        this.nom         = nom;
        this.biographie  = biographie;
        this.albums      = new ArrayList<>();
    }
 
    // --- Méthodes ---

    public void ajouterAlbum(Album a) {
        albums.add(a);
    }
    public boolean retirerAlbum(Album a) {
        return albums.remove(a);
    }
    public ArrayList<Morceau> getTousMorceaux() {
        ArrayList<Morceau> tousLesMorceaux = new ArrayList<>();
        for (Album album : albums) {
            for (Morceau m : album.getMorceaux()) {
                if (!tousLesMorceaux.contains(m)) {
                    tousLesMorceaux.add(m);
                }
            }
        }
        return tousLesMorceaux;
    }
    public int getNbAlbums() {
        return albums.size();
    }
 
    // --- Getters et Setters ---
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getBiographie() { return biographie; }
    public void setBiographie(String biographie) { this.biographie = biographie; }
    public ArrayList<Album> getAlbums() {
        return new ArrayList<>(albums);
    }
 
    @Override
    public String toString() {
        return "Artiste : " + nom
                + " | Albums : " + getNbAlbums()
                + "\nBiographie : " + biographie;
    }
}
