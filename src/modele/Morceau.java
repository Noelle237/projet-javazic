package modele;

import java.io.Serializable;

public class Morceau implements Serializable {
    private String titre;
    private int duree; // en secondes
    private String genre;
    private Album album;
    private Artiste artiste;
    private static final long serialVersionUID = 1L;

    public Morceau(String titre, int duree, String genre, Album album, Artiste artiste) {
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
        this.album = album;
        this.artiste = artiste;
    }

    public String getTitre() { return titre; }
    public int getDuree() { return duree; }
    public String getGenre() { return genre; }
    public Album getAlbum() { return album; }
    public Artiste getArtiste() { return artiste; }
    private String cheminFichier;

    public String getCheminFichier() { return cheminFichier; }
    public void setCheminFichier(String chemin) { cheminFichier = chemin; }

    public String getDureeFormatee() {
        return String.format("%d:%02d", duree / 60, duree % 60);
    }
}