
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Abonne;
import model.GestionnaireUtilisateurs;

public class AuthentificationController {

    @FXML private TextField fieldUsername;
    @FXML private PasswordField fieldPassword;
    @FXML private Label authTitle;
    @FXML private Label errorLabel;

    private String mode = "connexion"; // "connexion" ou "creer"
    private String roleVise = "abonne";

    // Appelé depuis BienvenueController pour définir le mode
    public void setMode(String mode) {
        this.mode = mode;
        if ("creer".equals(mode)) {
            authTitle.setText("Créer un compte");
        }
    }

    public void setRoleVise(String role) {
        this.roleVise = role;
        authTitle.setText("Connexion " + (role.equals("admin") ? "Administrateur" : "Abonné"));
    }

    @FXML
    private void handleConnexion(ActionEvent e) {
        String login = fieldUsername.getText().trim();
        String mdp   = fieldPassword.getText().trim();

        if (login.isEmpty() || mdp.isEmpty()) {
            afficherErreur("Veuillez remplir tous les champs.");
            return;
        }

        GestionnaireUtilisateurs gu = AppContext.getInstance().getGestionnaire();

        if ("creer".equals(mode)) {
            boolean ok = gu.inscrireAbonne(login, mdp, login);
            if (!ok) {
                afficherErreur("Ce login est déjà utilisé.");
                return;
            }
            ouvrirAccueil("abonne", e, gu.connecterAbonne(login, mdp));
        } else if ("admin".equals(roleVise)) {
            var admin = gu.connecterAdministrateur(login, mdp);
            if (admin == null) { afficherErreur("Identifiants incorrects."); return; }
            ouvrirAccueil("admin", e, null);
        } else {
            Abonne abonne = gu.connecterAbonne(login, mdp);
            if (abonne == null) { afficherErreur("Identifiants incorrects."); return; }
            if (abonne.estSuspendu()) { afficherErreur("Compte suspendu."); return; }
            ouvrirAccueil("abonne", e, abonne);
        }
    }

    @FXML
    private void handleAnnuler(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/bienvenue.fxml"));
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 800));
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void ouvrirAccueil(String role, ActionEvent e, Abonne abonne) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accueil.fxml"));
            Parent root = loader.load();
            AccueilController ctrl = loader.getController();
            ctrl.setRole(role);
            if (abonne != null) ctrl.setAbonne(abonne);
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 800));
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void afficherErreur(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}