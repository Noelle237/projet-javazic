
package modele;
import java.io.Serializable;

public class Artiste implements Serializable {
    private String nom;
    private String biographie;
    private static final long serialVersionUID = 1L;

    public Artiste(String nom, String biographie) {
        this.nom = nom;
        this.biographie = biographie;
    }

    public String getNom() { return nom; }
    public String getBiographie() { return biographie; }
    public void setNom(String nom) { this.nom = nom; }
    public void setBiographie(String biographie) { this.biographie = biographie; }
}