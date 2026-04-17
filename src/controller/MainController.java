package controller;

import model.*;
import view.IView;

import java.util.List;

public class MainController {

    private final IView view;
    private final Catalogue catalogue;
    private final List<Abonne> abonnes;
    private final Administrateur admin;

    private Utilisateur utilisateurConnecte;

    // Sous-contrôleurs spécialisés
    private final CatalogueController catalogueController;
    private final PlaylistController playlistController;
    private final UserController userController;

    // Constructeur principal, reçoit toutes les dépendances
    public MainController(IView view, Catalogue catalogue,
                          List<Abonne> abonnes, Administrateur admin) {
        this.view = view;
        this.catalogue = catalogue;
        this.abonnes = abonnes;
        this.admin = admin;

        this.catalogueController = new CatalogueController(view, catalogue);
        this.playlistController  = new PlaylistController(view, catalogue);
        this.userController      = new UserController(view, abonnes);
    }

    // Lance la boucle principale de l'application
    public void demarrer() {
        boolean quitter = false;

        while (!quitter) {
            view.afficherMenuPrincipal();
            int choix = view.lireEntierEntre(1, 5);

            switch (choix) {
                case 1 -> connexionAdmin();
                case 2 -> connexionAbonne();
                case 3 -> creerCompteAbonne();
                case 4 -> continuerVisiteur();
                case 5 -> quitter = true;
            }
        }

        sauvegarderEtQuitter();
    }

    // ── Connexion ───────────────────────────────────────────────

    // Gère la connexion en tant qu'administrateur
    private void connexionAdmin() {
        view.afficherMessage("=== Connexion Administrateur ===");
        String login = view.lireTexte("Login : ");
        String mdp   = view.lireTexte("Mot de passe : ");

        if (admin.seConnecter(login, mdp)) {
            utilisateurConnecte = admin;
            view.afficherMessage("Bienvenue, " + admin.getNom() + " !");
            menuAdmin();
        } else {
            view.afficherErreur("Identifiants incorrects.");
        }
        utilisateurConnecte = null;
    }

    // Gère la connexion en tant qu'abonné
    private void connexionAbonne() {
        view.afficherMessage("=== Connexion Abonné ===");
        String login = view.lireTexte("Login : ");
        String mdp   = view.lireTexte("Mot de passe : ");

        Abonne abonne = userController.trouverAbonne(login, mdp);
        if (abonne != null) {
            if (abonne.isSuspendu()) {
                view.afficherErreur("Votre compte est suspendu. Contactez l'administrateur.");
                return;
            }
            utilisateurConnecte = abonne;
            view.afficherMessage("Bienvenue, " + abonne.getNom() + " !");
            menuAbonne(abonne);
        } else {
            view.afficherErreur("Identifiants incorrects.");
        }
        utilisateurConnecte = null;
    }

    // Gère la création d'un nouveau compte abonné
    private void creerCompteAbonne() {
        view.afficherMessage("=== Créer un compte ===");
        String nom   = view.lireTexte("Votre nom : ");
        String login = view.lireTexte("Choisissez un login : ");
        String mdp   = view.lireTexte("Choisissez un mot de passe : ");

        userController.creerCompte(nom, login, mdp, abonnes);
    }

    // Lance une session en mode visiteur sans compte
    private void continuerVisiteur() {
        Visiteur visiteur = new Visiteur();
        utilisateurConnecte = visiteur;
        view.afficherMessage("Vous naviguez en mode visiteur (5 écoutes max par session).");
        menuVisiteur(visiteur);
        utilisateurConnecte = null;
    }

    // ── Menus par rôle ──────────────────────────────────────────

    // Menu de l'administrateur
    private void menuAdmin() {
        boolean retour = false;
        while (!retour) {
            view.afficherMenuAdmin();
            int choix = view.lireEntierEntre(1, 5);
            switch (choix) {
                case 1 -> catalogueController.menuGestionCatalogue();
                case 2 -> userController.menuGestionAbonnes(abonnes);
                case 3 -> userController.afficherStatistiques(catalogue, abonnes);
                case 4 -> retour = true;
                case 5 -> retour = true;
            }
        }
    }

    // Menu de l'abonné connecté
    private void menuAbonne(Abonne abonne) {
        boolean retour = false;
        while (!retour) {
            view.afficherMenuAbonne();
            int choix = view.lireEntierEntre(1, 6);
            switch (choix) {
                case 1 -> catalogueController.menuConsultation(abonne);
                case 2 -> playlistController.menuPlaylists(abonne);
                case 3 -> catalogueController.afficherHistorique(abonne);
                case 4 -> retour = true;
                case 5 -> retour = true;
            }
        }
    }

    // Menu du visiteur non connecté
    private void menuVisiteur(Visiteur visiteur) {
        boolean retour = false;
        while (!retour) {
            view.afficherMenuVisiteur();
            int choix = view.lireEntierEntre(1, 3);
            switch (choix) {
                case 1 -> catalogueController.menuConsultation(visiteur);
                case 2 -> retour = true;
            }
        }
    }

    // ── Sauvegarde ──────────────────────────────────────────────

    // Sauvegarde les données puis ferme l'application proprement
    private void sauvegarderEtQuitter() {
        view.afficherMessage("Sauvegarde en cours...");
        try {
            catalogue.sauvegarder("data/catalogue.ser");
            userController.sauvegarderAbonnes(abonnes, "data/users.ser");
            view.afficherMessage("Données sauvegardées. À bientôt !");
        } catch (Exception e) {
            view.afficherErreur("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // Retourne l'utilisateur actuellement connecté
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
}