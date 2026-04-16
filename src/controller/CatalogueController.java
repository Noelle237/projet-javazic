package controller;

import model.*;
import view.IView;

import java.util.List;

public class CatalogueController {

    private final IView view;
    private final Catalogue catalogue;

    // Nombre max d'écoutes autorisées pour un visiteur par session
    private static final int MAX_ECOUTES_VISITEUR = 5;

    // Constructeur
    public CatalogueController(IView view, Catalogue catalogue) {
        this.view = view;
        this.catalogue = catalogue;
    }

    // ── Menu consultation (Visiteur & Abonné) ───────────────────

    // Affiche le menu de consultation du catalogue
    public void menuConsultation(Utilisateur utilisateur) {
        boolean retour = false;
        while (!retour) {
            view.afficherMenuCatalogue();
            int choix = view.lireEntierEntre(1, 6);
            switch (choix) {
                case 1 -> rechercherEtAfficher(utilisateur);
                case 2 -> parcourirArtistes(utilisateur);
                case 3 -> parcourirAlbums(utilisateur);
                case 4 -> parcourirMorceaux(utilisateur);
                case 5 -> retour = true;
            }
        }
    }

    // ── Recherche ────────────────────────────────────────────────

    // Demande un mot-clé, cherche dans le catalogue et affiche les résultats
    public void rechercherEtAfficher(Utilisateur utilisateur) {
        String query = view.lireTexte("Rechercher (titre, artiste, album) : ");

        if (query == null || query.isBlank()) {
            view.afficherErreur("La recherche ne peut pas être vide.");
            return;
        }

        List<Morceau> resultats = catalogue.rechercher(query.trim());

        if (resultats.isEmpty()) {
            view.afficherMessage("Aucun résultat pour « " + query + " ».");
            return;
        }

        view.afficherListeMorceaux(resultats);
        int choix = view.lireEntierEntre(0, resultats.size(),
                "Sélectionner un morceau (0 = retour) : ");

        if (choix > 0) {
            menuDetailMorceau(resultats.get(choix - 1), utilisateur);
        }
    }

    // ── Parcourir par catégorie ──────────────────────────────────

    // Affiche la liste des artistes et permet d'en consulter un
    public void parcourirArtistes(Utilisateur utilisateur) {
        List<Artiste> artistes = catalogue.getArtistes();
        if (artistes.isEmpty()) {
            view.afficherMessage("Le catalogue ne contient aucun artiste.");
            return;
        }

        view.afficherListeArtistes(artistes);
        int choix = view.lireEntierEntre(0, artistes.size(),
                "Sélectionner un artiste (0 = retour) : ");

        if (choix > 0) {
            menuDetailArtiste(artistes.get(choix - 1), utilisateur);
        }
    }

    // Affiche la liste des albums et permet d'en consulter un
    public void parcourirAlbums(Utilisateur utilisateur) {
        List<Album> albums = catalogue.getAlbums();
        if (albums.isEmpty()) {
            view.afficherMessage("Le catalogue ne contient aucun album.");
            return;
        }

        view.afficherListeAlbums(albums);
        int choix = view.lireEntierEntre(0, albums.size(),
                "Sélectionner un album (0 = retour) : ");

        if (choix > 0) {
            menuDetailAlbum(albums.get(choix - 1), utilisateur);
        }
    }

    // Affiche tous les morceaux du catalogue
    public void parcourirMorceaux(Utilisateur utilisateur) {
        List<Morceau> morceaux = catalogue.getMorceaux();
        if (morceaux.isEmpty()) {
            view.afficherMessage("Le catalogue ne contient aucun morceau.");
            return;
        }

        view.afficherListeMorceaux(morceaux);
        int choix = view.lireEntierEntre(0, morceaux.size(),
                "Sélectionner un morceau (0 = retour) : ");

        if (choix > 0) {
            menuDetailMorceau(morceaux.get(choix - 1), utilisateur);
        }
    }

    // ── Détails ─────────────────────────────────────────────────

    // Affiche les détails d'un morceau et propose des actions
    public void menuDetailMorceau(Morceau morceau, Utilisateur utilisateur) {
        view.afficherDetailMorceau(morceau);
        view.afficherMenuDetailMorceau(utilisateur instanceof Abonne);
        int choix = view.lireEntierEntre(1, 4);

        switch (choix) {
            case 1 -> jouerMorceau(morceau, utilisateur);
            case 2 -> menuDetailArtiste(morceau.getArtiste(), utilisateur);
            case 3 -> {
                // On récupère le premier album contenant ce morceau
                List<Album> albums = catalogue.getAlbumsDuMorceau(morceau);
                if (albums.isEmpty()) {
                    view.afficherMessage("Ce morceau n'appartient à aucun album.");
                } else {
                    menuDetailAlbum(albums.get(0), utilisateur);
                }
            }
            case 4 -> { /* retour */ }
        }
    }

    // Affiche les détails d'un artiste et sa discographie
    public void menuDetailArtiste(Artiste artiste, Utilisateur utilisateur) {
        if (artiste == null) {
            view.afficherErreur("Artiste introuvable.");
            return;
        }
        view.afficherDetailArtiste(artiste);
        List<Album> albums = artiste.getAlbums();

        if (!albums.isEmpty()) {
            int choix = view.lireEntierEntre(0, albums.size(),
                    "Voir un album (0 = retour) : ");
            if (choix > 0) {
                menuDetailAlbum(albums.get(choix - 1), utilisateur);
            }
        }
    }

    // Affiche les détails d'un album et la liste de ses morceaux
    public void menuDetailAlbum(Album album, Utilisateur utilisateur) {
        if (album == null) {
            view.afficherErreur("Album introuvable.");
            return;
        }
        view.afficherDetailAlbum(album);
        List<Morceau> morceaux = album.getMorceaux();

        if (!morceaux.isEmpty()) {
            int choix = view.lireEntierEntre(0, morceaux.size(),
                    "Écouter un morceau (0 = retour) : ");
            if (choix > 0) {
                menuDetailMorceau(morceaux.get(choix - 1), utilisateur);
            }
        }
    }

    // ── Lecture ─────────────────────────────────────────────────

    // Joue un morceau en respectant le quota visiteur (5 écoutes max)
    public void jouerMorceau(Morceau morceau, Utilisateur utilisateur) {
        if (utilisateur instanceof Visiteur visiteur) {
            if (visiteur.getNbEcouteSession() >= MAX_ECOUTES_VISITEUR) {
                view.afficherErreur(
                    "Limite de " + MAX_ECOUTES_VISITEUR +
                    " écoutes atteinte. Créez un compte pour écouter sans limite !");
                return;
            }
            visiteur.incrementerEcoutes();
        }

        // On ajoute à l'historique si c'est un abonné
        if (utilisateur instanceof Abonne abonne) {
            abonne.ajouterAHistorique(morceau);
        }

        morceau.incrementerNbEcoutes();
        view.simulerLecture(morceau);
    }

    // ── Historique ───────────────────────────────────────────────

    // Affiche l'historique d'écoute de l'abonné
    public void afficherHistorique(Abonne abonne) {
        List<Morceau> historique = abonne.getHistorique();
        if (historique.isEmpty()) {
            view.afficherMessage("Votre historique est vide.");
        } else {
            view.afficherMessage("=== Votre historique d'écoute ===");
            view.afficherListeMorceaux(historique);
        }
    }

    // ── Gestion admin du catalogue ───────────────────────────────

    // Menu de gestion du catalogue réservé à l'administrateur
    public void menuGestionCatalogue() {
        boolean retour = false;
        while (!retour) {
            view.afficherMenuGestionCatalogue();
            int choix = view.lireEntierEntre(1, 6);
            switch (choix) {
                case 1 -> ajouterMorceau();
                case 2 -> supprimerMorceau();
                case 3 -> ajouterAlbum();
                case 4 -> ajouterArtiste();
                case 5 -> retour = true;
            }
        }
    }

    // Ajoute un nouveau morceau au catalogue
    private void ajouterMorceau() {
        String titre = view.lireTexte("Titre du morceau : ");
        String genre = view.lireTexte("Genre : ");
        int duree    = view.lireEntierEntre(1, 3600, "Durée (secondes) : ");
        int annee    = view.lireEntierEntre(1900, 2100, "Année de sortie : ");

        // Choisir un artiste existant ou en créer un nouveau
        view.afficherListeArtistes(catalogue.getArtistes());
        int choixArtiste = view.lireEntierEntre(0, catalogue.getArtistes().size(),
                "Sélectionner un artiste (0 = nouveau) : ");

        Artiste artiste;
        if (choixArtiste == 0) {
            String nom = view.lireTexte("Nom de l'artiste : ");
            artiste = new Artiste(nom);
            catalogue.ajouterArtiste(artiste);
        } else {
            artiste = catalogue.getArtistes().get(choixArtiste - 1);
        }

        Morceau morceau = new Morceau(titre, duree, genre, annee, artiste);

        try {
            catalogue.ajouterMorceau(morceau);
            view.afficherMessage("Morceau « " + titre + " » ajouté avec succès.");
        } catch (Exception e) {
            view.afficherErreur("Erreur : " + e.getMessage());
        }
    }

    // Supprime un morceau du catalogue
    private void supprimerMorceau() {
        List<Morceau> morceaux = catalogue.getMorceaux();
        if (morceaux.isEmpty()) {
            view.afficherMessage("Aucun morceau à supprimer.");
            return;
        }
        view.afficherListeMorceaux(morceaux);
        int choix = view.lireEntierEntre(0, morceaux.size(),
                "Supprimer le morceau n° (0 = annuler) : ");

        if (choix > 0) {
            Morceau cible = morceaux.get(choix - 1);
            catalogue.supprimerMorceau(cible);
            view.afficherMessage("Morceau supprimé.");
        }
    }

    // Ajoute un nouvel album au catalogue
    private void ajouterAlbum() {
        String titre = view.lireTexte("Titre de l'album : ");
        int annee    = view.lireEntierEntre(1900, 2100, "Année de sortie : ");
        String type  = view.lireTexte("Type (studio / live / compilation) : ");

        Album album = new Album(titre, annee, type);
        catalogue.ajouterAlbum(album);
        view.afficherMessage("Album « " + titre + " » ajouté.");
    }

    // Ajoute un nouvel artiste au catalogue
    private void ajouterArtiste() {
        String nom = view.lireTexte("Nom de l'artiste / groupe : ");
        String bio = view.lireTexte("Biographie courte : ");

        Artiste artiste = new Artiste(nom, bio);
        catalogue.ajouterArtiste(artiste);
        view.afficherMessage("Artiste « " + nom + " » ajouté.");
    }
}