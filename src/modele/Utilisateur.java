package modele;

public abstract class Utilisateur {
    protected String identifiant;
    protected String motDePasse;
    protected String nom;
    protected String prenom;
    protected String email;

    public Utilisateur(String identifiant, String motDePasse, String nom, String prenom, String email) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public String getIdentifiant() { return identifiant; }
    public String getMotDePasse() { return motDePasse; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}