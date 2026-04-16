import model.*;
import exceptions.MorceauDejaPresent;

// Peuple le catalogue avec des données d'exemple au premier lancement du programme
// Permet de tester toutes les fonctionnalités sans saisie manuelle
public class DonneesExemple {

    public static void peupler(Catalogue catalogue) {

        // --- Groupes et artistes ---

        Groupe theBeatles = new Groupe("The Beatles", "Groupe de rock britannique (1960-1970).");
        Artiste lennon    = new Artiste("John Lennon", "Chanteur et compositeur des Beatles.");
        Artiste mccartney = new Artiste("Paul McCartney", "Bassiste et compositeur des Beatles.");
        theBeatles.ajouterMembre(lennon);
        theBeatles.ajouterMembre(mccartney);

        Artiste michaelJackson = new Artiste("Michael Jackson", "Roi de la pop (1958-2009).");
        Artiste davidBowie     = new Artiste("David Bowie", "Chanteur et compositeur britannique (1947-2016).");
        Groupe  daftPunk       = new Groupe("Daft Punk", "Duo de musique électronique français.");

        catalogue.ajouterArtiste(theBeatles);
        catalogue.ajouterArtiste(michaelJackson);
        catalogue.ajouterArtiste(davidBowie);
        catalogue.ajouterArtiste(daftPunk);

        // --- Morceaux ---

        Morceau heyJude       = new Morceau("Hey Jude",            431, "Rock",       1968, theBeatles);
        Morceau letItBe       = new Morceau("Let It Be",           243, "Rock",       1970, theBeatles);
        Morceau comeTogether  = new Morceau("Come Together",       259, "Rock",       1969, theBeatles);
        Morceau billie        = new Morceau("Billie Jean",         294, "Pop",        1982, michaelJackson);
        Morceau thriller      = new Morceau("Thriller",            358, "Pop",        1982, michaelJackson);
        Morceau beatIt        = new Morceau("Beat It",             258, "Pop",        1982, michaelJackson);
        Morceau starman       = new Morceau("Starman",             254, "Glam rock",  1972, davidBowie);
        Morceau heroes        = new Morceau("Heroes",              366, "Rock",       1977, davidBowie);
        Morceau getLucky      = new Morceau("Get Lucky",           369, "Electronic", 2013, daftPunk);
        Morceau aroundWorld   = new Morceau("Around the World",    428, "Electronic", 1997, daftPunk);

        // Ajout des morceaux au catalogue
        try {
            catalogue.ajouterMorceau(heyJude);
            catalogue.ajouterMorceau(letItBe);
            catalogue.ajouterMorceau(comeTogether);
            catalogue.ajouterMorceau(billie);
            catalogue.ajouterMorceau(thriller);
            catalogue.ajouterMorceau(beatIt);
            catalogue.ajouterMorceau(starman);
            catalogue.ajouterMorceau(heroes);
            catalogue.ajouterMorceau(getLucky);
            catalogue.ajouterMorceau(aroundWorld);
        } catch (MorceauDejaPresent e) {
            System.out.println("Doublon ignoré : " + e.getMessage());
        }

        // --- Albums ---

        Album abbey = new Album("Abbey Road", 1969, Album.TYPE_STUDIO, theBeatles);
        Album let   = new Album("Let It Be",  1970, Album.TYPE_STUDIO, theBeatles);
        Album mj    = new Album("Thriller",   1982, Album.TYPE_STUDIO, michaelJackson);
        Album bowie = new Album("Heroes",     1977, Album.TYPE_STUDIO, davidBowie);
        Album ram   = new Album("Random Access Memories", 2013, Album.TYPE_STUDIO, daftPunk);

        try {
            abbey.ajouterMorceau(heyJude);
            abbey.ajouterMorceau(comeTogether);
            let.ajouterMorceau(letItBe);
            mj.ajouterMorceau(billie);
            mj.ajouterMorceau(thriller);
            mj.ajouterMorceau(beatIt);
            bowie.ajouterMorceau(heroes);
            bowie.ajouterMorceau(starman);
            ram.ajouterMorceau(getLucky);
            ram.ajouterMorceau(aroundWorld);
        } catch (MorceauDejaPresent e) {
            System.out.println("Doublon album ignoré : " + e.getMessage());
        }

        // Lien album <-> artiste
        theBeatles.ajouterAlbum(abbey);
        theBeatles.ajouterAlbum(let);
        michaelJackson.ajouterAlbum(mj);
        davidBowie.ajouterAlbum(bowie);
        daftPunk.ajouterAlbum(ram);

        catalogue.ajouterAlbum(abbey);
        catalogue.ajouterAlbum(let);
        catalogue.ajouterAlbum(mj);
        catalogue.ajouterAlbum(bowie);
        catalogue.ajouterAlbum(ram);

        System.out.println("Catalogue d'exemple chargé : " + catalogue.getMorceaux().size() + " morceaux.");
    }
}