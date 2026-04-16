package model;
import java.io.Serializable;
import java.util.ArrayList;
class Album implements Serializable {
 
    // Types d'albums possibles (utilisés comme constantes)
    public static final String TYPE_STUDIO      = "studio";
    public static final String TYPE_LIVE        = "live";
    public static final String TYPE_COMPILATION = "compilation";
 
    // --- Attributs ---
    private String             titre;    
    private int                annee;  
    private String             type;     
    private ArrayList<Morceau> morceaux; 
 
    // --- Constructeur ---
 
    public Album(String titre, int annee, String type) {
        this.titre    = titre;
        this.annee    = annee;
        this.type     = type;
        this.morceaux = new ArrayList<>();
    }
 
    // --- Méthodes ---
    public void ajouterMorceau(Morceau m) {
        morceaux.add(m);
    }
    public boolean retirerMorceau(Morceau m) {
        return morceaux.remove(m);
    }
    public int getDureeTotal() {
        int total = 0;
        for (Morceau m : morceaux) {
            total += m.getDuree();
        }
        return total;
    }
    public String getDureeTotalFormatee() {
        int total    = getDureeTotal();
        int minutes  = total / 60;
        int secondes = total % 60;
        return minutes + "min " + String.format("%02d", secondes) + "s";
    }
    public boolean contient(Morceau m) {
        return morceaux.contains(m);
    }
    public int getNbMorceaux() {
        return morceaux.size();
    }
 
    // --- Getters et Setters ---
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public int getAnnee() { return annee; }
    public String getType() { return type; }
    public ArrayList<Morceau> getMorceaux() {
        return new ArrayList<>(morceaux);
    }
 
    @Override
    public String toString() {
        return "Album : \"" + titre + "\""
                + " (" + annee + ")"
                + " [" + type + "]"
                + " — " + getNbMorceaux() + " morceaux"
                + " — Durée : " + getDureeTotalFormatee();
    }
}
