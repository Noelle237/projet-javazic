package controlleur;

import modele.Abonne;
import modele.Album;
import modele.Artiste;
import modele.Morceau;

public class AppContext {
    private static AppContext instance;
    private Abonne abonneCourant;
    private Album albumCourant;
    private Artiste artisteCourant;

    private AppContext() {}

    public static AppContext getInstance() {
        if (instance == null) instance = new AppContext();
        return instance;
    }
    
    private Morceau morceauCourant;

    public Morceau getMorceauCourant() { return morceauCourant; }
    public void setMorceauCourant(Morceau m) { morceauCourant = m; } 
    
    public Abonne getAbonneCourant() { return abonneCourant; }
    public void setAbonneCourant(Abonne a) { abonneCourant = a; }

    public Album getAlbumCourant() { return albumCourant; }
    public void setAlbumCourant(Album a) { albumCourant = a; }

    public Artiste getArtisteCourant() { return artisteCourant; }
    public void setArtisteCourant(Artiste a) { artisteCourant = a; }
}