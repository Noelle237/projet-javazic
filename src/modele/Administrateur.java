package modele;

public class Administrateur extends Utilisateur {
    public Administrateur(String identifiant, String motDePasse, String nom, String prenom, String email) {
        super(identifiant, motDePasse, nom, prenom, email);
    }
}