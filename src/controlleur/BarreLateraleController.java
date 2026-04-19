package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class BarreLateraleController implements Initializable {

    @FXML private Button navCatalogue;
    @FXML private Button navPlaylists;
    @FXML private Button navHistorique;
    @FXML private Button navAdmin;
    @FXML private Button btnLogout;
    @FXML private Label roleLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modele.Abonne abonne = AppContext.getInstance().getAbonneCourant();
        if (abonne == null) {
            roleLabel.setText("Visiteur");
            navHistorique.setVisible(false);
            navHistorique.setManaged(false);
            navPlaylists.setVisible(false);
            navPlaylists.setManaged(false);
        } else {
            roleLabel.setText("Abonné");
        }
    }

    @FXML private void goToCatalogue() { chargerVue("/view/catalogue.fxml"); }
    @FXML private void goToPlaylists() { chargerVue("/view/playlist.fxml"); }
    @FXML private void goToHistorique() { chargerVue("/view/historique.fxml"); }
    @FXML private void goToAdmin() { chargerVue("/view/administrareur.fxml"); }

    @FXML
    private void handleLogout() {
        AppContext.getInstance().setAbonneCourant(null);
        chargerVue("/view/bienvenue.fxml");
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}