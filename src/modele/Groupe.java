package modele;

import java.util.List;
import java.io.Serializable;

public class Groupe extends Artiste  implements Serializable {
    private List<String> membres;
    private static final long serialVersionUID = 1L;

    public Groupe(String nom, String biographie, List<String> membres) {
        super(nom, biographie);
        this.membres = membres;
    }

    public List<String> getMembres() { return membres; }
    public void setMembres(List<String> membres) { this.membres = membres; }
}