package controlleur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BienvenueController {

    @FXML private Button btnAdmin;
    @FXML private Button btnClient;
    @FXML private Button btnCreer;
    @FXML private Button btnVisiteur;
    @FXML private Button btnQuitter;

    @FXML
    private void handleAdmin() {
        AppContext.getInstance().setModeConnexion("admin");
        chargerVue("/view/authentification.fxml");
    }

    @FXML
    private void handleClient() {
        AppContext.getInstance().setModeConnexion("client");
        chargerVue("/view/authentification.fxml");
    }

    @FXML
    private void handleCreer() {
        AppContext.getInstance().setModeConnexion("creer");
        chargerVue("/view/authentification.fxml");
    }

    @FXML
    private void handleVisiteur() {
        AppContext.getInstance().setAbonneCourant(null);
        chargerVue("/view/accueil.fxml");
    }

    @FXML
    private void handleQuitter() {
        Platform.exit();
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnVisiteur.getScene().getWindow();
            stage.setScene(new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}