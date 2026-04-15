class Administrateur extends Utilisateur {

    // --- Constructeur ---
    public Administrateur(String login, String motDePasse, String nom) {
        super(login, motDePasse, nom);
    }

    // --- Méthodes de gestion des comptes ---

    public void suspendreCompte(Abonne a) throws CompteDejaModifie {
        if (a.estSuspendu()) {
            throw new CompteDejaModifie("Le compte de " + a.getNom() + " est déjà suspendu.");
        }
        a.suspendre();
        System.out.println("[Admin] Compte de " + a.getNom() + " suspendu.");
    }

    public void reactiverCompte(Abonne a) throws CompteDejaModifie {
        if (!a.estSuspendu()) {
            throw new CompteDejaModifie("Le compte de " + a.getNom() + " n'est pas suspendu.");
        }
        a.reactiver();
        System.out.println("[Admin] Compte de " + a.getNom() + " réactivé.");
    }

    public void supprimerCompte(Abonne a, ArrayList<Abonne> abonnes)
            throws CompteInexistant {
        if (!abonnes.remove(a)) {
            throw new CompteInexistant("L'abonné '" + a.getLogin() + "' est introuvable.");
        }
        System.out.println("[Admin] Compte de " + a.getNom() + " supprimé.");
    }

    // --- Méthodes de statistiques ---

    public void afficherStats(Catalogue catalogue, ArrayList<Abonne> abonnes) {
        System.out.println("========== STATISTIQUES JAVAZIC ==========");
        System.out.println("Nombre d'abonnés        : " + abonnes.size());
        System.out.println("Nombre de morceaux      : " + catalogue.getMorceaux().size());
        System.out.println("Nombre d'albums         : " + catalogue.getAlbums().size());
        System.out.println("Nombre d'artistes       : " + catalogue.getArtistes().size());
        System.out.println("Nombre total d'écoutes  : " + catalogue.getNbEcoutesTotal());
        System.out.println("==========================================");
    }

    @Override
    public String getRole() {
        return "Administrateur";
    }

    @Override
    public String toString() {
        return "[Administrateur] " + nom + " (login: " + login + ")";
    }
}

