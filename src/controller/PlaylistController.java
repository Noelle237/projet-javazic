package controller;

import model.*;
import view.IView;

import java.util.List;

public class PlaylistController {

    private final IView view;
    private final Catalogue catalogue;

    // Constructeur
    public PlaylistController(IView view, Catalogue catalogue) {
        this.view = view;
        this.catalogue = catalogue;
    }

    // ── Menu principal playlists ─────────────────────────────────

    // Affiche le menu de gestion des playlists de l'abonné
    public void menuPlaylists(Abonne abonne) {
        boolean retour = false;
        while (!retour) {
            view.afficherMenuPlaylists(abonne.getPlaylists());
            int choix = view.lireEntierEntre(1, 6);
            switch (choix) {
                case 1 -> creerPlaylist(abonne);
                case 2 -> selectionnerEtGererPlaylist(abonne);
                case 3 -> renommerPlaylist(abonne);
                case 4 -> supprimerPlaylist(abonne);
                case 5 -> retour = true;
            }
        }
    }

    // ── CRUD Playlist ────────────────────────────────────────────

    // Crée une nouvelle playlist pour l'abonné
    public void creerPlaylist(Abonne abonne) {
        String nom = view.lireTexte("Nom de la nouvelle playlist : ");

        if (nom == null || nom.isBlank()) {
            view.afficherErreur("Le nom de la playlist ne peut pas être vide.");
            return;
        }

        // Vérifier qu'une playlist du même nom n'existe pas déjà
        boolean existe = abonne.getPlaylists().stream()
                .anyMatch(p -> p.getNom().equalsIgnoreCase(nom.trim()));

        if (existe) {
            view.afficherErreur("Vous avez déjà une playlist nommée « " + nom + " ».");
            return;
        }

        abonne.creerPlaylist(nom.trim());
        view.afficherMessage("Playlist « " + nom.trim() + " » créée !");
    }

    // Renomme une playlist existante de l'abonné
    public void renommerPlaylist(Abonne abonne) {
        PlayList playlist = choisirPlaylist(abonne);
        if (playlist == null) return;

        String nouveauNom = view.lireTexte("Nouveau nom : ");
        if (nouveauNom == null || nouveauNom.isBlank()) {
            view.afficherErreur("Le nom ne peut pas être vide.");
            return;
        }

        String ancienNom = playlist.getNom();
        playlist.setNom(nouveauNom.trim());
        view.afficherMessage("Playlist « " + ancienNom + " » renommée en « " + nouveauNom.trim() + " ».");
    }

    // Supprime une playlist de l'abonné après confirmation
    public void supprimerPlaylist(Abonne abonne) {
        PlayList playlist = choisirPlaylist(abonne);
        if (playlist == null) return;

        String confirmation = view.lireTexte(
                "Supprimer « " + playlist.getNom() + " » ? (oui/non) : ");

        if ("oui".equalsIgnoreCase(confirmation.trim())) {
            abonne.supprimerPlaylist(playlist);
            view.afficherMessage("Playlist supprimée.");
        } else {
            view.afficherMessage("Suppression annulée.");
        }
    }

    // ── Gestion du contenu d'une playlist ───────────────────────

    // Sélectionne une playlist et ouvre son menu de gestion
    public void selectionnerEtGererPlaylist(Abonne abonne) {
        PlayList playlist = choisirPlaylist(abonne);
        if (playlist == null) return;
        menuGestionContenu(abonne, playlist);
    }

    // Menu de gestion du contenu d'une playlist
    public void menuGestionContenu(Abonne abonne, PlayList playlist) {
        boolean retour = false;
        while (!retour) {
            view.afficherDetailPlaylist(playlist);
            view.afficherMenuContenuPlaylist();
            int choix = view.lireEntierEntre(1, 5);
            switch (choix) {
                case 1 -> ajouterMorceauDepuisCatalogue(playlist);
                case 2 -> ajouterMorceauDepuisPlaylist(abonne, playlist);
                case 3 -> retirerMorceau(playlist);
                case 4 -> lireToutePlaylist(playlist, abonne);
                case 5 -> retour = true;
            }
        }
    }

    // Recherche un morceau dans le catalogue et l'ajoute à la playlist
    public void ajouterMorceauDepuisCatalogue(PlayList playlist) {
        String query = view.lireTexte("Rechercher un morceau à ajouter : ");
        List<Morceau> resultats = catalogue.rechercher(query.trim());

        if (resultats.isEmpty()) {
            view.afficherMessage("Aucun résultat pour « " + query + " ».");
            return;
        }

        view.afficherListeMorceaux(resultats);
        int choix = view.lireEntierEntre(0, resultats.size(),
                "Ajouter le morceau n° (0 = annuler) : ");

        if (choix > 0) {
            ajouterMorceauAPlaylist(resultats.get(choix - 1), playlist);
        }
    }

    // Copie un morceau depuis une autre playlist de l'abonné
    public void ajouterMorceauDepuisPlaylist(Abonne abonne, PlayList cible) {
        // On filtre la playlist courante pour ne pas l'afficher comme source
        List<PlayList> autres = abonne.getPlaylists().stream()
                .filter(p -> !p.equals(cible))
                .toList();

        if (autres.isEmpty()) {
            view.afficherMessage("Vous n'avez pas d'autre playlist.");
            return;
        }

        view.afficherMessage("=== Choisir la playlist source ===");
        for (int i = 0; i < autres.size(); i++) {
            view.afficherMessage((i + 1) + ". " + autres.get(i).getNom());
        }
        int choixPlaylist = view.lireEntierEntre(0, autres.size(),
                "Source (0 = annuler) : ");
        if (choixPlaylist == 0) return;

        PlayList source = autres.get(choixPlaylist - 1);
        if (source.getMorceaux().isEmpty()) {
            view.afficherMessage("Cette playlist est vide.");
            return;
        }

        view.afficherListeMorceaux(source.getMorceaux());
        int choixMorceau = view.lireEntierEntre(0, source.getMorceaux().size(),
                "Morceau à copier (0 = annuler) : ");

        if (choixMorceau > 0) {
            ajouterMorceauAPlaylist(source.getMorceaux().get(choixMorceau - 1), cible);
        }
    }

    // Retire un morceau d'une playlist
    public void retirerMorceau(PlayList playlist) {
        if (playlist.getMorceaux().isEmpty()) {
            view.afficherMessage("Cette playlist est vide.");
            return;
        }

        view.afficherListeMorceaux(playlist.getMorceaux());
        int choix = view.lireEntierEntre(0, playlist.getMorceaux().size(),
                "Retirer le morceau n° (0 = annuler) : ");

        if (choix > 0) {
            Morceau morceau = playlist.getMorceaux().get(choix - 1);
            playlist.retirer(morceau);
            view.afficherMessage("« " + morceau.getTitre() + " » retiré de la playlist.");
        }
    }

    // Lit tous les morceaux d'une playlist en séquence
    public void lireToutePlaylist(PlayList playlist, Abonne abonne) {
        if (playlist.getMorceaux().isEmpty()) {
            view.afficherMessage("Cette playlist est vide.");
            return;
        }

        view.afficherMessage("▶  Lecture de « " + playlist.getNom() + " » — "
                + playlist.getMorceaux().size() + " morceaux");

        for (Morceau morceau : playlist.getMorceaux()) {
            abonne.ajouterAHistorique(morceau);
            morceau.incrementerNbEcoutes();
            view.simulerLecture(morceau);
        }

        view.afficherMessage("✓  Lecture terminée.");
    }

    // ── Helpers privés ───────────────────────────────────────────

    // Affiche les playlists et retourne celle choisie, null si annulation
    private PlayList choisirPlaylist(Abonne abonne) {
        List<PlayList> playlists = abonne.getPlaylists();
        if (playlists.isEmpty()) {
            view.afficherMessage("Vous n'avez aucune playlist. Créez-en une d'abord.");
            return null;
        }

        view.afficherListePlaylists(playlists);
        int choix = view.lireEntierEntre(0, playlists.size(),
                "Sélectionner une playlist (0 = annuler) : ");

        return (choix > 0) ? playlists.get(choix - 1) : null;
    }

    // Tente d'ajouter un morceau à une playlist en gérant le doublon
    private void ajouterMorceauAPlaylist(Morceau morceau, PlayList playlist) {
        try {
            playlist.ajouter(morceau);
            view.afficherMessage("« " + morceau.getTitre()
                    + " » ajouté à « " + playlist.getNom() + " ».");
        } catch (MorceauDejaPresent e) {
            view.afficherErreur("Ce morceau est déjà dans la playlist.");
        }
    }
}