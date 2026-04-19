package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Artiste;
import modele.Album;
import modele.Gestionnaire;
import java.net.URL;
import java.util.ResourceBundle;

public class ArtisteController implements Initializable {

    @FXML private Label labelNom;
    @FXML private Label labelBiographie;
    @FXML private ListView<String> listeAlbums;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Artiste artiste = AppContext.getInstance().getArtisteCourant();
        if (artiste != null) {
            labelNom.setText(artiste.getNom());
            labelBiographie.setText(artiste.getBiographie());
            ObservableList<String> albums = FXCollections.observableArrayList();
            for (Album a : Gestionnaire.getInstance().getCatalogue().getAlbums()) {
                if (a.getArtiste().getNom().equals(artiste.getNom())) {
                    albums.add(a.getTitre() + " (" + a.getAnnee() + ")");
                }
            }
            listeAlbums.setItems(albums);
        }
    }
}