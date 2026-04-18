package controlleur;

import modele.Abonne;
import modele.Album;
import modele.Artiste;

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

    public Abonne getAbonneCourant() { return abonneCourant; }
    public void setAbonneCourant(Abonne a) { abonneCourant = a; }

    public Album getAlbumCourant() { return albumCourant; }
    public void setAlbumCourant(Album a) { albumCourant = a; }

    public Artiste getArtisteCourant() { return artisteCourant; }
    public void setArtisteCourant(Artiste a) { artisteCourant = a; }
}