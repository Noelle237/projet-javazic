package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Catalogue;
import modele.Gestionnaire;
import modele.Morceau;
import modele.Album;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogueController implements Initializable {

    @FXML private TextField champRecherche;
    @FXML private ListView<String> listeMorceaux;
    @FXML private ListView<String> listeAlbums;

    private Catalogue catalogue;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogue = Gestionnaire.getInstance().getCatalogue();
        afficherTout();
    }

    private void afficherTout() {
        ObservableList<String> morceaux = FXCollections.observableArrayList();
        for (Morceau m : catalogue.getMorceaux()) {
            morceaux.add(m.getTitre() + " — " + m.getArtiste().getNom());
        }
        listeMorceaux.setItems(morceaux);

        ObservableList<String> albums = FXCollections.observableArrayList();
        for (Album a : catalogue.getAlbums()) {
            albums.add(a.getTitre() + " (" + a.getAnnee() + ")");
        }
        listeAlbums.setItems(albums);
    }

    @FXML
    private void rechercher() {
        String query = champRecherche.getText();
        ObservableList<String> resultats = FXCollections.observableArrayList();
        for (Morceau m : catalogue.rechercherParTitre(query)) {
            resultats.add(m.getTitre() + " — " + m.getArtiste().getNom());
        }
        listeMorceaux.setItems(resultats);
    }
}