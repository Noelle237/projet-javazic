package modele;

public class Morceau {
    private String titre;
    private int duree; // en secondes
    private String genre;
    private Album album;
    private Artiste artiste;

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

    public String getDureeFormatee() {
        return String.format("%d:%02d", duree / 60, duree % 60);
    }
}