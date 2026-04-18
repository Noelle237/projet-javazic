package model;
import java.io.Serializable;
import java.util.ArrayList;
 
class Morceau implements Serializable {
 
    // --- Attributs ---
    private String  titre;      
    private int     duree;      
    private String  genre;     
    private int     annee;      
    private Artiste artiste;   
    private int     nbEcoutes;  
 
    // --- Constructeur ---
 
  
    public Morceau(String titre, int duree, String genre, int annee, Artiste artiste) {
        this.titre     = titre;
        this.duree     = duree;
        this.genre     = genre;
        this.annee     = annee;
        this.artiste   = artiste;
        this.nbEcoutes = 0; 
    }
 
    // --- Méthodes ---
 
    public void jouer() {
        nbEcoutes++; // On compte l'écoute dès le lancement
 
        System.out.println("▶  Lecture : " + titre
                + " — " + artiste.getNom()
                + "  [" + getDureeFormatee() + "]");
 
        // Simulation de la progression (10 étapes)
        int etapes = 10;
        for (int i = 1; i <= etapes; i++) {
            // Barre de progression : [=====>    ]
            int rempli = i;
            int vide   = etapes - i;
            System.out.print("\r[");
            for (int j = 0; j < rempli - 1; j++) System.out.print("=");
            System.out.print(">");
            for (int j = 0; j < vide; j++)   System.out.print(" ");
            System.out.print("] " + (i * 10) + "%");
 
            try {
                // Pause proportionnelle à la durée réelle du morceau
                // On divise la durée totale en 10 étapes (max 2s par étape pour ne pas bloquer)
                int pauseMs = Math.min((duree * 100), 2000) / etapes;
                Thread.sleep(pauseMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\nLecture interrompue.");
                return;
            }
        }
        System.out.println("\n✓  Fin de lecture : " + titre);
    }
 
    public String getDureeFormatee() {
        int minutes  = duree / 60;
        int secondes = duree % 60;
        return minutes + "min " + String.format("%02d", secondes) + "s";
    }
 
    // --- Getters et Setters ---
 
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public int getDuree() { return duree; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getAnnee() { return annee; }
    public Artiste getArtiste() { return artiste; }
    public void setArtiste(Artiste artiste) { this.artiste = artiste; }
    public int getNbEcoutes() { return nbEcoutes; }
 
    @Override
    public String toString() {
        return "\"" + titre + "\""
                + " — " + artiste.getNom()
                + " (" + annee + ")"
                + " | " + genre
                + " | " + getDureeFormatee()
                + " | Écoutes : " + nbEcoutes;
    }
} 
