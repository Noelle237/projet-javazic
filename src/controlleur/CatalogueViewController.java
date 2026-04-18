package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import modele.Album;
import modele.Artiste;
import modele.Catalogue;
import modele.Gestionnaire;
import modele.Morceau;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogueViewController implements Initializable {

    @FXML private TextField champRecherche;
    @FXML private TabPane tabPane;
    @FXML private ListView<String> listeMorceaux;
    @FXML private ListView<String> listeAlbums;
    @FXML private ListView<String> listeArtistes;

    private Catalogue catalogue;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogue = Gestionnaire.getInstance().getCatalogue();
        afficherTout();

        // Clic sur un album → ouvrir la vue album
        listeAlbums.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                int index = listeAlbums.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    Album album = catalogue.getAlbums().get(index);
                    AppContext.getInstance().setAlbumCourant(album);
                    chargerVue("/view/album.fxml");
                }
            }
        });

        // Clic sur un artiste → ouvrir la vue artiste
        listeArtistes.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                int index = listeArtistes.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    Artiste artiste = catalogue.getArtistes().get(index);
                    AppContext.getInstance().setArtisteCourant(artiste);
                    chargerVue("/view/artiste.fxml");
                }
            }
        });

        // Clic sur un morceau → ouvrir la vue musique
        listeMorceaux.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                int index = listeMorceaux.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    Morceau morceau = catalogue.getMorceaux().get(index);
                    AppContext.getInstance().setMorceauCourant(morceau);
                    chargerVue("/view/musique.fxml");
                }
            }
        });
    }

    private void afficherTout() {
        // Morceaux
        ObservableList<String> morceaux = FXCollections.observableArrayList();
        for (Morceau m : catalogue.getMorceaux()) {
            morceaux.add(m.getTitre() + " — " + m.getArtiste().getNom() + " (" + m.getDureeFormatee() + ")");
        }
        listeMorceaux.setItems(morceaux);

        // Albums
        ObservableList<String> albums = FXCollections.observableArrayList();
        for (Album a : catalogue.getAlbums()) {
            albums.add(a.getTitre() + " — " + a.getArtiste().getNom() + " (" + a.getAnnee() + ")");
        }
        listeAlbums.setItems(albums);

        // Artistes
        ObservableList<String> artistes = FXCollections.observableArrayList();
        for (Artiste a : catalogue.getArtistes()) {
            artistes.add(a.getNom());
        }
        listeArtistes.setItems(artistes);
    }

    @FXML
    private void rechercher() {
        String query = champRecherche.getText().trim();
        if (query.isEmpty()) {
            afficherTout();
            return;
        }

        ObservableList<String> morceaux = FXCollections.observableArrayList();
        for (Morceau m : catalogue.rechercherParTitre(query)) {
            morceaux.add(m.getTitre() + " — " + m.getArtiste().getNom());
        }
        listeMorceaux.setItems(morceaux);

        ObservableList<String> albums = FXCollections.observableArrayList();
        for (Album a : catalogue.rechercherAlbumParTitre(query)) {
            albums.add(a.getTitre() + " (" + a.getAnnee() + ")");
        }
        listeAlbums.setItems(albums);
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) champRecherche.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}