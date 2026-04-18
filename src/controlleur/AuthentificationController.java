package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modele.Gestionnaire;
import modele.Utilisateur;
import modele.Abonne;

public class AuthentificationController {

    @FXML private TextField champIdentifiant;
    @FXML private PasswordField champMotDePasse;
    @FXML private Label labelErreur;

    @FXML
    private void seConnecter() {
        String id = champIdentifiant.getText().trim();
        String mdp = champMotDePasse.getText().trim();
        Utilisateur u = Gestionnaire.getInstance().authentifier(id, mdp);
        if (u != null) {
            if (u instanceof Abonne) {
                AppContext.getInstance().setAbonneCourant((Abonne) u);
            }
            chargerVue("/view/accueil.fxml");
        } else {
            labelErreur.setText("Identifiant ou mot de passe incorrect.");
        }
    }

    @FXML
    private void annuler() {
        chargerVue("/view/bienvenue.fxml");
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