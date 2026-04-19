package modele;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Catalogue implements Serializable {
    private List<Album> albums;
    private List<Morceau> morceaux;
    private List<Artiste> artistes;
    private static final long serialVersionUID = 1L;

    public Catalogue() {
        this.albums = new ArrayList<>();
        this.morceaux = new ArrayList<>();
        this.artistes = new ArrayList<>();
    }

    public void ajouterAlbum(Album a) { albums.add(a); }
    public void ajouterMorceau(Morceau m) { morceaux.add(m); }
    public void ajouterArtiste(Artiste a) { artistes.add(a); }

    public List<Album> getAlbums() { return albums; }
    public List<Morceau> getMorceaux() { return morceaux; }
    public List<Artiste> getArtistes() { return artistes; }

    public List<Morceau> rechercherParTitre(String titre) {
        return morceaux.stream()
            .filter(m -> m.getTitre().toLowerCase().contains(titre.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Album> rechercherAlbumParTitre(String titre) {
        return albums.stream()
            .filter(a -> a.getTitre().toLowerCase().contains(titre.toLowerCase()))
            .collect(Collectors.toList());
    }
}