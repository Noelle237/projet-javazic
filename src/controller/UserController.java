package controller;

import model.*;
import view.IView;

import java.io.*;
import java.util.List;

public class UserController {

    private final IView view;
    private final List<Abonne> abonnes;

    // Constructeur
    public UserController(IView view, List<Abonne> abonnes) {
        this.view = view;
        this.abonnes = abonnes;
    }

    // ── Authentification ─────────────────────────────────────────

    // Recherche un abonné par login et mot de passe, retourne null si non trouvé
    public Abonne trouverAbonne(String login, String mdp) {
        return abonnes.stream()
                .filter(a -> a.seConnecter(login, mdp))
                .findFirst()
                .orElse(null);
    }

    // ── Création de compte ───────────────────────────────────────

    // Crée un nouveau compte abonné après validation des données saisies
    public void creerCompte(String nom, String login, String mdp,
                            List<Abonne> abonnes) {
        // Validation des champs
        if (nom == null || nom.isBlank()) {
            view.afficherErreur("Le nom ne peut pas être vide.");
            return;
        }
        if (login == null || login.isBlank()) {
            view.afficherErreur("Le login ne peut pas être vide.");
            return;
        }
        if (mdp == null || mdp.length() < 4) {
            view.afficherErreur("Le mot de passe doit comporter au moins 4 caractères.");
            return;
        }

        // Vérifier que le login n'est pas déjà pris
        boolean loginPris = abonnes.stream()
                .anyMatch(a -> a.getLogin().equalsIgnoreCase(login.trim()));
        if (loginPris) {
            view.afficherErreur("Ce login est déjà utilisé. Choisissez-en un autre.");
            return;
        }

        Abonne nouvelAbonne = new Abonne(nom.trim(), login.trim(), mdp);
        abonnes.add(nouvelAbonne);
        view.afficherMessage("Compte créé avec succès ! Bienvenue, " + nom.trim() + " !");
    }

    // ── Gestion admin des abonnés ────────────────────────────────

    // Menu de gestion des comptes abonnés, réservé à l'administrateur
    public void menuGestionAbonnes(List<Abonne> abonnes) {
        boolean retour = false;
        while (!retour) {
            view.afficherMenuGestionAbonnes();
            int choix = view.lireEntierEntre(1, 5);
            switch (choix) {
                case 1 -> listerAbonnes(abonnes);
                case 2 -> suspendreCompte(abonnes);
                case 3 -> reactiverCompte(abonnes);
                case 4 -> supprimerCompte(abonnes);
                case 5 -> retour = true;
            }
        }
    }

    // Affiche tous les abonnés avec leur statut (actif / suspendu)
    public void listerAbonnes(List<Abonne> abonnes) {
        if (abonnes.isEmpty()) {
            view.afficherMessage("Aucun abonné enregistré.");
            return;
        }
        view.afficherMessage("=== Liste des abonnés (" + abonnes.size() + ") ===");
        for (int i = 0; i < abonnes.size(); i++) {
            Abonne a = abonnes.get(i);
            String statut = a.isSuspendu() ? " [SUSPENDU]" : "";
            view.afficherMessage((i + 1) + ". " + a.getNom()
                    + " (@" + a.getLogin() + ")" + statut
                    + " — " + a.getPlaylists().size() + " playlist(s)");
        }
    }

    // Suspend le compte d'un abonné sélectionné
    public void suspendreCompte(List<Abonne> abonnes) {
        Abonne cible = choisirAbonne(abonnes, "suspendre");
        if (cible == null) return;

        if (cible.isSuspendu()) {
            view.afficherErreur("Ce compte est déjà suspendu.");
            return;
        }

        cible.setSuspendu(true);
        view.afficherMessage("Compte de « " + cible.getNom() + " » suspendu.");
    }

    // Réactive le compte suspendu d'un abonné
    public void reactiverCompte(List<Abonne> abonnes) {
        Abonne cible = choisirAbonne(abonnes, "réactiver");
        if (cible == null) return;

        if (!cible.isSuspendu()) {
            view.afficherErreur("Ce compte est déjà actif.");
            return;
        }

        cible.setSuspendu(false);
        view.afficherMessage("Compte de « " + cible.getNom() + " » réactivé.");
    }

    // Supprime définitivement un compte abonné après confirmation
    public void supprimerCompte(List<Abonne> abonnes) {
        Abonne cible = choisirAbonne(abonnes, "supprimer");
        if (cible == null) return;

        String confirmation = view.lireTexte(
                "Supprimer définitivement « " + cible.getNom() + " » ? (oui/non) : ");

        if ("oui".equalsIgnoreCase(confirmation.trim())) {
            abonnes.remove(cible);
            view.afficherMessage("Compte supprimé définitivement.");
        } else {
            view.afficherMessage("Suppression annulée.");
        }
    }

    // ── Statistiques ─────────────────────────────────────────────

    // Calcule et affiche les statistiques globales de l'application
    public void afficherStatistiques(Catalogue catalogue, List<Abonne> abonnes) {
        int nbMorceaux   = catalogue.getMorceaux().size();
        int nbAlbums     = catalogue.getAlbums().size();
        int nbArtistes   = catalogue.getArtistes().size();
        int nbAbonnes    = abonnes.size();
        int nbSuspendus  = (int) abonnes.stream().filter(Abonne::isSuspendu).count();
        int totalEcoutes = catalogue.getNbEcoutesTotal();

        // Morceau le plus écouté
        Morceau topMorceau = catalogue.getMorceaux().stream()
                .max((a, b) -> Integer.compare(a.getNbEcoutes(), b.getNbEcoutes()))
                .orElse(null);

        view.afficherMessage("╔══════════════════════════════════╗");
        view.afficherMessage("║       STATISTIQUES JAVAZIC        ║");
        view.afficherMessage("╠══════════════════════════════════╣");
        view.afficherMessage("║ Morceaux     : " + padLeft(nbMorceaux, 17)   + " ║");
        view.afficherMessage("║ Albums       : " + padLeft(nbAlbums, 17)     + " ║");
        view.afficherMessage("║ Artistes     : " + padLeft(nbArtistes, 17)   + " ║");
        view.afficherMessage("║ Abonnés      : " + padLeft(nbAbonnes, 17)    + " ║");
        view.afficherMessage("║ Suspendus    : " + padLeft(nbSuspendus, 17)  + " ║");
        view.afficherMessage("║ Total écoutes: " + padLeft(totalEcoutes, 17) + " ║");
        if (topMorceau != null) {
            view.afficherMessage("╠══════════════════════════════════╣");
            view.afficherMessage("║ Top morceau  : " + truncate(topMorceau.getTitre(), 17) + " ║");
            view.afficherMessage("║ Écoutes      : " + padLeft(topMorceau.getNbEcoutes(), 17) + " ║");
        }
        view.afficherMessage("╚══════════════════════════════════╝");
    }

    // ── Persistance ──────────────────────────────────────────────

    // Sauvegarde la liste des abonnés dans un fichier par sérialisation
    public void sauvegarderAbonnes(List<Abonne> abonnes, String chemin)
            throws IOException {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(abonnes);
        }
    }

    // Charge la liste des abonnés depuis un fichier sérialisé
    // Retourne une liste vide si le fichier n'existe pas encore
    @SuppressWarnings("unchecked")
    public List<Abonne> chargerAbonnes(String chemin) {
        File fichier = new File(chemin);
        if (!fichier.exists()) {
            view.afficherMessage("Aucune donnée abonné trouvée. Démarrage à vide.");
            return new java.util.ArrayList<>();
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fichier))) {
            return (List<Abonne>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            view.afficherErreur("Erreur de chargement des abonnés : " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    // ── Helpers privés ───────────────────────────────────────────

    // Affiche la liste des abonnés et retourne celui sélectionné, null si annulation
    private Abonne choisirAbonne(List<Abonne> abonnes, String action) {
        if (abonnes.isEmpty()) {
            view.afficherMessage("Aucun abonné enregistré.");
            return null;
        }
        listerAbonnes(abonnes);
        int choix = view.lireEntierEntre(0, abonnes.size(),
                "Abonné à " + action + " (0 = annuler) : ");
        return (choix > 0) ? abonnes.get(choix - 1) : null;
    }

    // Aligne un entier à droite sur une largeur donnée
    private String padLeft(int valeur, int largeur) {
        String s = String.valueOf(valeur);
        return " ".repeat(Math.max(0, largeur - s.length())) + s;
    }

    // Tronque un texte à la largeur donnée avec "…" si nécessaire
    private String truncate(String texte, int largeur) {
        if (texte.length() <= largeur) {
            return texte + " ".repeat(largeur - texte.length());
        }
        return texte.substring(0, largeur - 1) + "…";
    }
}