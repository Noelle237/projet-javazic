package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Album;
import modele.Morceau;
import java.net.URL;
import java.util.ResourceBundle;

public class AlbumController implements Initializable {

    @FXML private Label labelTitre;
    @FXML private Label labelArtiste;
    @FXML private Label labelAnnee;
    @FXML private ListView<String> listeMorceaux;

    private Album album;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // L'album est injecté via AppContext
        album = AppContext.getInstance().getAlbumCourant();
        if (album != null) {
            labelTitre.setText(album.getTitre());
            labelArtiste.setText(album.getArtiste().getNom());
            labelAnnee.setText(String.valueOf(album.getAnnee()));
            ObservableList<String> morceaux = FXCollections.observableArrayList();
            for (Morceau m : album.getMorceaux()) {
                morceaux.add(m.getTitre() + " — " + m.getDureeFormatee());
            }
            listeMorceaux.setItems(morceaux);
        }
    }
}