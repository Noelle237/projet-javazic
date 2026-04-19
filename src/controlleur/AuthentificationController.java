package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Abonne;
import modele.Administrateur;
import modele.Gestionnaire;
import modele.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthentificationController implements Initializable {

    @FXML private Label authTitle;
    @FXML private TextField fieldUsername;
    @FXML private PasswordField fieldPassword;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.setVisible(false);
        String mode = AppContext.getInstance().getModeConnexion();
        switch (mode) {
            case "admin" -> {
                boolean adminExiste = Gestionnaire.getInstance().getUtilisateurs()
                    .stream().anyMatch(u -> u instanceof Administrateur);
                if (!adminExiste) {
                    authTitle.setText("Créer un compte Administrateur");
                } else {
                    authTitle.setText("Connexion Administrateur");
                }
            }
            case "creer" -> authTitle.setText("Créer un compte client");
            default -> authTitle.setText("Connexion Client");
        }
    }

    @FXML
    private void handleConnexion() {
        String id  = fieldUsername.getText().trim();
        String mdp = fieldPassword.getText().trim();

        if (id.isEmpty() || mdp.isEmpty()) {
            afficherErreur("Veuillez remplir tous les champs.");
            return;
        }

        String mode = AppContext.getInstance().getModeConnexion();

        switch (mode) {

            case "creer" -> {
                if (Gestionnaire.getInstance().identifiantExiste(id)) {
                    afficherErreur("Cet identifiant est déjà utilisé.");
                    return;
                }
                Abonne nouvel = new Abonne(id, mdp, id, "", id + "@javazic.fr");
                Gestionnaire.getInstance().ajouterUtilisateur(nouvel);
                Gestionnaire.getInstance().sauvegarder();
                AppContext.getInstance().setAbonneCourant(nouvel);
                chargerVue("/view/accueil.fxml");
            }

            case "admin" -> {
                boolean adminExiste = Gestionnaire.getInstance().getUtilisateurs()
                    .stream().anyMatch(u -> u instanceof Administrateur);

                if (!adminExiste) {
                    Administrateur admin = new Administrateur(id, mdp, id, "", id + "@javazic.fr");
                    Gestionnaire.getInstance().ajouterUtilisateur(admin);
                    Gestionnaire.getInstance().sauvegarder();
                    AppContext.getInstance().setModeConnexion("admin");
                    chargerVue("/view/administrareur.fxml");
                } else {
                    Utilisateur u = Gestionnaire.getInstance().authentifier(id, mdp);
                    if (u == null || !(u instanceof Administrateur)) {
                        afficherErreur("Identifiant ou mot de passe incorrect.");
                        return;
                    }
                    AppContext.getInstance().setModeConnexion("admin");
                    chargerVue("/view/administrareur.fxml");
                }
            }

            default -> {
                Utilisateur u = Gestionnaire.getInstance().authentifier(id, mdp);
                if (u == null) {
                    afficherErreur("Identifiant ou mot de passe incorrect.");
                    return;
                }
                if (u instanceof Abonne) {
                    AppContext.getInstance().setAbonneCourant((Abonne) u);
                }
                chargerVue("/view/accueil.fxml");
            }
        }
    }

    @FXML
    private void handleAnnuler() {
        chargerVue("/view/bienvenue.fxml");
    }

    private void afficherErreur(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) fieldUsername.getScene().getWindow();
            stage.setScene(new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}