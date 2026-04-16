import controller.MainController;
import model.*;
import view.ConsoleView;
import view.IView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Chemins des fichiers de sauvegarde
    private static final String CHEMIN_CATALOGUE = "data/catalogue.ser";
    private static final String CHEMIN_USERS     = "data/users.ser";

    public static void main(String[] args) {

        // Création de la vue (changer ConsoleView par GraphicView pour la version graphique)
        IView vue = new ConsoleView();

        vue.afficherMessage("╔══════════════════════════════════╗");
        vue.afficherMessage("║         Bienvenue sur            ║");
        vue.afficherMessage("║          J A V A Z I C           ║");
        vue.afficherMessage("╚══════════════════════════════════╝");

        // Chargement du catalogue
        Catalogue catalogue = chargerCatalogue(vue);

        // Chargement des abonnés
        List<Abonne> abonnes = chargerAbonnes(vue);

        // Création du compte administrateur par défaut
        // À changer dans un vrai projet (login/mdp stockés de façon sécurisée)
        Administrateur admin = new Administrateur("Admin", "admin", "admin1234");

        // Création et lancement du contrôleur principal
        MainController mainController = new MainController(vue, catalogue, abonnes, admin);
        mainController.demarrer();
    }

    // Charge le catalogue depuis le fichier de sauvegarde
    // Si le fichier n'existe pas, retourne un catalogue vide avec des données de démo
    @SuppressWarnings("unchecked")
    private static Catalogue chargerCatalogue(IView vue) {
        File fichier = new File(CHEMIN_CATALOGUE);
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
                vue.afficherMessage("Catalogue chargé.");
                return (Catalogue) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                vue.afficherErreur("Erreur chargement catalogue : " + e.getMessage());
            }
        }

        // Aucun fichier trouvé : on crée un catalogue de démo pour tester
        vue.afficherMessage("Aucune sauvegarde trouvée. Chargement des données de démonstration...");
        return creerCatalogueDemo();
    }

    // Charge la liste des abonnés depuis le fichier de sauvegarde
    // Retourne une liste vide si aucun fichier n'existe
    @SuppressWarnings("unchecked")
    private static List<Abonne> chargerAbonnes(IView vue) {
        File fichier = new File(CHEMIN_USERS);
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
                vue.afficherMessage("Comptes utilisateurs chargés.");
                return (List<Abonne>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                vue.afficherErreur("Erreur chargement utilisateurs : " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    // Crée un catalogue de démonstration pour pouvoir tester l'appli dès le lancement
    private static Catalogue creerCatalogueDemo() {
        Catalogue catalogue = new Catalogue();

        // Artistes
        Artiste beatles  = new Artiste("The Beatles",  "Groupe britannique légendaire des années 60.");
        Artiste daftpunk = new Artiste("Daft Punk",    "Duo électronique français, icônes de la French Touch.");
        Artiste adele    = new Artiste("Adele",        "Chanteuse britannique aux multiples Grammy Awards.");

        catalogue.ajouterArtiste(beatles);
        catalogue.ajouterArtiste(daftpunk);
        catalogue.ajouterArtiste(adele);

        // Albums
        Album abbeyRoad    = new Album("Abbey Road",       1969, "studio");
        Album randomAccess = new Album("Random Access Memories", 2013, "studio");
        Album adele21      = new Album("21",               2011, "studio");

        catalogue.ajouterAlbum(abbeyRoad);
        catalogue.ajouterAlbum(randomAccess);
        catalogue.ajouterAlbum(adele21);

        // Morceaux Beatles
        Morceau comeTogether = new Morceau("Come Together", 259, "Rock", 1969, beatles);
        Morceau something    = new Morceau("Something",     182, "Rock", 1969, beatles);
        Morceau hereComesTheSun = new Morceau("Here Comes the Sun", 185, "Rock", 1969, beatles);
        abbeyRoad.ajouterMorceau(comeTogether);
        abbeyRoad.ajouterMorceau(something);
        abbeyRoad.ajouterMorceau(hereComesTheSun);

        // Morceaux Daft Punk
        Morceau getlucky     = new Morceau("Get Lucky",          369, "Electro", 2013, daftpunk);
        Morceau instantCrush = new Morceau("Instant Crush",      337, "Electro", 2013, daftpunk);
        Morceau georgio      = new Morceau("Giorgio by Moroder", 540, "Electro", 2013, daftpunk);
        randomAccess.ajouterMorceau(getlucky);
        randomAccess.ajouterMorceau(instantCrush);
        randomAccess.ajouterMorceau(georgio);

        // Morceaux Adele
        Morceau rollingInTheDeep = new Morceau("Rolling in the Deep", 228, "Soul", 2011, adele);
        Morceau someonelikeYou   = new Morceau("Someone Like You",    285, "Soul", 2011, adele);
        adele21.ajouterMorceau(rollingInTheDeep);
        adele21.ajouterMorceau(someonelikeYou);

        // Ajout de tous les morceaux au catalogue
        catalogue.ajouterMorceau(comeTogether);
        catalogue.ajouterMorceau(something);
        catalogue.ajouterMorceau(hereComesTheSun);
        catalogue.ajouterMorceau(getlucky);
        catalogue.ajouterMorceau(instantCrush);
        catalogue.ajouterMorceau(georgio);
        catalogue.ajouterMorceau(rollingInTheDeep);
        catalogue.ajouterMorceau(someonelikeYou);

        return catalogue;
    }
}