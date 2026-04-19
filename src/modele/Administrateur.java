package modele;

import java.io.Serializable;


public class Administrateur extends Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
    public Administrateur(String identifiant, String motDePasse, String nom, String prenom, String email) {
        super(identifiant, motDePasse, nom, prenom, email);
    }
}