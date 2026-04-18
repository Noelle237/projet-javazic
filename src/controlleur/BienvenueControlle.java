package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modele.Gestionnaire;
import modele.Utilisateur;

public class BienvenueController {

    @FXML private TextField champIdentifiant;
    @FXML private PasswordField champMotDePasse;
    @FXML private Label labelErreur;

    @FXML
    private void seConnecter() {
        String id = champIdentifiant.getText();
        String mdp = champMotDePasse.getText();
        Utilisateur u = Gestionnaire.getInstance().authentifier(id, mdp);
        if (u != null) {
            chargerVue("/view/accueil.fxml");
        } else {
            labelErreur.setText("Identifiant ou mot de passe incorrect.");
        }
    }

    @FXML
    private void continuerVisiteur() {
        chargerVue("/view/accueil.fxml");
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) champIdentifiant.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}