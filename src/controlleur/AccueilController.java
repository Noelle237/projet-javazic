package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AccueilController {

    @FXML private Button btnCatalogue;

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
    private void seDeconnecter() {
        chargerVue("/view/bienvenue.fxml");
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnCatalogue.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}