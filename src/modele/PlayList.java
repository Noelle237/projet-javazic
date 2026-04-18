package modele;

import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private String nom;
    private List<Morceau> morceaux;

    public PlayList(String nom) {
        this.nom = nom;
        this.morceaux = new ArrayList<>();
    }

    public String getNom() { return nom; }
    public List<Morceau> getMorceaux() { return morceaux; }
    public void ajouterMorceau(Morceau m) { morceaux.add(m); }
    public void supprimerMorceau(Morceau m) { morceaux.remove(m); }
}