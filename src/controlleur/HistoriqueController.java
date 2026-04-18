package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Abonne;
import modele.Morceau;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoriqueController implements Initializable {

    @FXML private ListView<String> listeHistorique;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Abonne abonne = AppContext.getInstance().getAbonneCourant();
        ObservableList<String> historique = FXCollections.observableArrayList();
        if (abonne != null) {
            for (Morceau m : abonne.getHistorique()) {
                historique.add(m.getTitre() + " — " + m.getArtiste().getNom());
            }
        }
        listeHistorique.setItems(historique);
    }
}