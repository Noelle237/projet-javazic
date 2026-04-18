package model;
import java.io.Serializable;
import java.util.ArrayList;

class Catalogue implements Serializable {
 
    // --- Attributs ---
    private ArrayList<Morceau>  morceaux;  
    private ArrayList<Album>    albums;   
    private ArrayList<Artiste>  artistes; 
 
    // --- Constructeur ---
    public Catalogue() {
        this.morceaux = new ArrayList<>();
        this.albums   = new ArrayList<>();
        this.artistes = new ArrayList<>();
    }
 
    public ArrayList<Morceau> rechercher(String q) {
        ArrayList<Morceau> resultats = new ArrayList<>();
        String motCle = q.toLowerCase().trim();
 
        for (Morceau m : morceaux) {
            boolean matchTitre   = m.getTitre().toLowerCase().contains(motCle);
            boolean matchArtiste = m.getArtiste().getNom().toLowerCase().contains(motCle);
            boolean matchGenre   = m.getGenre().toLowerCase().contains(motCle);
 
            if (matchTitre || matchArtiste || matchGenre) {
                resultats.add(m);
            }
        }
        return resultats;
    }
    public ArrayList<Album> rechercherAlbums(String q) {
        ArrayList<Album> resultats = new ArrayList<>();
        String motCle = q.toLowerCase().trim();
 
        for (Album a : albums) {
            if (a.getTitre().toLowerCase().contains(motCle)) {
                resultats.add(a);
            }
        }
        return resultats;
    }
    public ArrayList<Artiste> rechercherArtistes(String q) {
        ArrayList<Artiste> resultats = new ArrayList<>();
        String motCle = q.toLowerCase().trim();
 
        for (Artiste a : artistes) {
            if (a.getNom().toLowerCase().contains(motCle)) {
                resultats.add(a);
            }
        }
        return resultats;
    }
    public ArrayList<Morceau> getMorceauxParArtiste(Artiste artiste) {
        ArrayList<Morceau> resultats = new ArrayList<>();
        for (Morceau m : morceaux) {
            if (m.getArtiste().equals(artiste)) {
                resultats.add(m);
            }
        }
        return resultats;
    }
    public ArrayList<Morceau> getMorceauxParGenre(String genre) {
        ArrayList<Morceau> resultats = new ArrayList<>();
        for (Morceau m : morceaux) {
            if (m.getGenre().equalsIgnoreCase(genre)) {
                resultats.add(m);
            }
        }
        return resultats;
    }
    public ArrayList<Morceau> getMorceauxParAnnee(int annee) {
        ArrayList<Morceau> resultats = new ArrayList<>();
        for (Morceau m : morceaux) {
            if (m.getAnnee() == annee) {
                resultats.add(m);
            }
        }
        return resultats;
    }
    public void ajouterMorceau(Morceau m) throws MorceauDejaPresent {
        if (morceaux.contains(m)) {
            throw new MorceauDejaPresent(
                "Le morceau '" + m.getTitre() + "' est déjà dans le catalogue."
            );
        }
        morceaux.add(m);
    }
 
    public void ajouterAlbum(Album a) {
        if (!albums.contains(a)) {
            albums.add(a);
        }
    }
    public void ajouterArtiste(Artiste a) {
        if (!artistes.contains(a)) {
            artistes.add(a);
        }
    }
    public void supprimerMorceau(Morceau m) throws MorceauIntrouvable {
        if (!morceaux.remove(m)) {
            throw new MorceauIntrouvable(
                "Le morceau '" + m.getTitre() + "' est introuvable dans le catalogue."
            );
        }
    }
    public boolean supprimerAlbum(Album a) {
        return albums.remove(a);
    }
    public boolean supprimerArtiste(Artiste a) {
        return artistes.remove(a);
    }
    public int getNbEcoutesTotal() {
        int total = 0;
        for (Morceau m : morceaux) {
            total += m.getNbEcoutes();
        }
        return total;
    }
    public Morceau getMorceauLePlusEcoute() {
        if (morceaux.isEmpty()) return null;
 
        Morceau champion = morceaux.get(0);
        for (Morceau m : morceaux) {
            if (m.getNbEcoutes() > champion.getNbEcoutes()) {
                champion = m;
            }
        }
        return champion;
    }
    public ArrayList<Morceau> getMorceaux() {
        return new ArrayList<>(morceaux);
    }
    public ArrayList<Album> getAlbums() {
        return new ArrayList<>(albums);
    }
    public ArrayList<Artiste> getArtistes() {
        return new ArrayList<>(artistes);
    }
    public int getNbMorceaux() { return morceaux.size(); }
    public int getNbAlbums() { return albums.size(); }
    public int getNbArtistes() { return artistes.size(); }
 
    @Override
    public String toString() {
        return "=== Catalogue JAVAZIC ==="
                + "\nMorceaux  : " + getNbMorceaux()
                + "\nAlbums    : " + getNbAlbums()
                + "\nArtistes  : " + getNbArtistes()
                + "\nÉcoutes totales : " + getNbEcoutesTotal();
    }
}