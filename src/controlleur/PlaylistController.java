package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Abonne;
import modele.PlayList;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    @FXML private ListView<String> listePlaylists;
    @FXML private TextField champNomPlaylist;

    private Abonne abonne;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        abonne = AppContext.getInstance().getAbonneCourant();
        rafraichir();
    }

    private void rafraichir() {
        if (abonne == null) return;
        ObservableList<String> noms = FXCollections.observableArrayList();
        for (PlayList p : abonne.getPlaylists()) {
            noms.add(p.getNom());
        }
        listePlaylists.setItems(noms);
    }

    @FXML
    private void creerPlaylist() {
        String nom = champNomPlaylist.getText().trim();
        if (!nom.isEmpty() && abonne != null) {
            abonne.ajouterPlaylist(new PlayList(nom));
            champNomPlaylist.clear();
            rafraichir();
        }
    }
}