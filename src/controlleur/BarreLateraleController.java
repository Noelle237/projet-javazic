package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BarreLateraleController {

    @FXML private Button btnAccueil;

    @FXML
    private void allerAccueil() {
        chargerVue("/view/accueil.fxml");
    }

    @FXML
    private void allerCatalogue() {
        chargerVue("/view/catalogue.fxml");
    }

    @FXML
    private void allerPlaylists() {
        chargerVue("/view/playlist.fxml");
    }

    @FXML
    private void allerHistorique() {
        chargerVue("/view/historique.fxml");
    }

    @FXML
    private void allerAdmin() {
        chargerVue("/view/administrateur.fxml");
    }

    @FXML
    private void seDeconnecter() {
        AppContext.getInstance().setAbonneCourant(null);
        chargerVue("/view/bienvenue.fxml");
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnAccueil.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}