package modele;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private String titre;
    private int annee;
    private Artiste artiste;
    private String genre;
    private String coverUrl;
    private List<Morceau> morceaux;

    public Album(String titre, int annee, Artiste artiste, String genre, String coverUrl) {
        this.titre = titre;
        this.annee = annee;
        this.artiste = artiste;
        this.genre = genre;
        this.coverUrl = coverUrl;
        this.morceaux = new ArrayList<>();
    }

    public String getTitre() { return titre; }
    public int getAnnee() { return annee; }
    public Artiste getArtiste() { return artiste; }
    public String getGenre() { return genre; }
    public String getCoverUrl() { return coverUrl; }
    public List<Morceau> getMorceaux() { return morceaux; }
    public void ajouterMorceau(Morceau m) { morceaux.add(m); }
}