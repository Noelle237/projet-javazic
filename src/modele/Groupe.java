package modele;

import java.util.List;

public class Groupe extends Artiste {
    private List<String> membres;

    public Groupe(String nom, String biographie, List<String> membres) {
        super(nom, biographie);
        this.membres = membres;
    }

    public List<String> getMembres() { return membres; }
    public void setMembres(List<String> membres) { this.membres = membres; }
}