package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Abonne;
import modele.Administrateur;
import modele.Gestionnaire;

import java.net.URL;
import java.util.ResourceBundle;

public class InscriptionController implements Initializable {

    @FXML private Label inscriptionTitle;
    @FXML private TextField fieldIdentifiant;
    @FXML private PasswordField fieldMotDePasse;
    @FXML private PasswordField fieldConfirmation;
    @FXML private TextField fieldNom;
    @FXML private TextField fieldPrenom;
    @FXML private TextField fieldEmail;
    @FXML private Label errorLabel;
    @FXML private Button btnInscrire;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        String mode = AppContext.getInstance().getModeConnexion();
        if ("admin".equals(mode)) {
            inscriptionTitle.setText("Créer un compte Administrateur");
            btnInscrire.setText("Créer le compte admin");
        } else {
            inscriptionTitle.setText("Créer un compte Client");
        }
    }

    @FXML
    private void handleInscrire() {
        String id      = fieldIdentifiant.getText().trim();
        String mdp     = fieldMotDePasse.getText().trim();
        String confirm = fieldConfirmation.getText().trim();
        String nom     = fieldNom.getText().trim();
        String prenom  = fieldPrenom.getText().trim();
        String email   = fieldEmail.getText().trim();

        // Vérifications
        if (id.isEmpty() || mdp.isEmpty() || confirm.isEmpty() 
                || nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            afficherErreur("Veuillez remplir tous les champs.");
            return;
        }
        if (!mdp.equals(confirm)) {
            afficherErreur("Les mots de passe ne correspondent pas.");
            return;
        }
        if (mdp.length() < 6) {
            afficherErreur("Le mot de passe doit contenir au moins 6 caractères.");
            return;
        }
        if (!email.contains("@")) {
            afficherErreur("Email invalide.");
            return;
        }
        if (Gestionnaire.getInstance().identifiantExiste(id)) {
            afficherErreur("Cet identifiant est déjà utilisé.");
            return;
        }

        // Création du compte
        String mode = AppContext.getInstance().getModeConnexion();
        if ("admin".equals(mode)) {
            Administrateur admin = new Administrateur(id, mdp, nom, prenom, email);
            Gestionnaire.getInstance().ajouterUtilisateur(admin);
            Gestionnaire.getInstance().sauvegarder();
            AppContext.getInstance().setModeConnexion("admin");
            chargerVue("/view/administrareur.fxml");
        } else {
            Abonne nouvel = new Abonne(id, mdp, nom, prenom, email);
            Gestionnaire.getInstance().ajouterUtilisateur(nouvel);
            Gestionnaire.getInstance().sauvegarder();
            AppContext.getInstance().setAbonneCourant(nouvel);
            chargerVue("/view/accueil.fxml");
        }
    }

    @FXML
    private void handleAnnuler() {
        chargerVue("/view/bienvenue.fxml");
    }

    private void afficherErreur(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void chargerVue(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) fieldIdentifiant.getScene().getWindow();
            stage.setScene(new Scene(root, 
                stage.getScene().getWidth(), 
                stage.getScene().getHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}