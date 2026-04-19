package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Abonne extends Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<PlayList> playlists;
    private List<Morceau> historique;

    public Abonne(String identifiant, String motDePasse, String nom, String prenom, String email) {
        super(identifiant, motDePasse, nom, prenom, email);
        this.playlists = new ArrayList<>();
        this.historique = new ArrayList<>();
    }

    public List<PlayList> getPlaylists() { return playlists; }
    public List<Morceau> getHistorique() { return historique; }
    public void ajouterPlaylist(PlayList p) { playlists.add(p); }
    public void ajouterHistorique(Morceau m) { historique.add(m); }
}