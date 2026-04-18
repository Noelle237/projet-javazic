package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Gestionnaire;
import modele.Utilisateur;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private ListView<String> listeUtilisateurs;
    @FXML private Label labelStats;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rafraichir();
    }

    private void rafraichir() {
        ObservableList<String> users = FXCollections.observableArrayList();
        for (Utilisateur u : Gestionnaire.getInstance().getUtilisateurs()) {
            users.add(u.getIdentifiant() + " — " + u.getClass().getSimpleName());
        }
        listeUtilisateurs.setItems(users);
        labelStats.setText("Total utilisateurs : " + Gestionnaire.getInstance().getUtilisateurs().size());
    }
}