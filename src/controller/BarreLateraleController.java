package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BarreLateraleController {

    @FXML private Label roleLabel;
    @FXML private Button navHistorique;
    @FXML private Button navAdmin;
    @FXML private Button navPlaylists;

    private AccueilController accueilController;

    public void setRole(String role, AccueilController accueil) {
        this.accueilController = accueil;

        // Afficher le rôle
        switch (role) {
            case "admin"   -> roleLabel.setText("Administrateur");
            case "abonne"  -> roleLabel.setText("Abonné");
            default        -> roleLabel.setText("Visiteur");
        }

        // Afficher/cacher selon le rôle
        boolean estVisiteur = role.equals("visiteur");
        boolean estAdmin    = role.equals("admin");

        navHistorique.setVisible(!estVisiteur);
        navHistorique.setManaged(!estVisiteur);
        navPlaylists.setVisible(!estVisiteur);
        navPlaylists.setManaged(!estVisiteur);
        navAdmin.setVisible(estAdmin);
        navAdmin.setManaged(estAdmin);
    }

    @FXML private void goToCatalogue()  { accueilController.navigateTo("/view/catalogue.fxml"); }
    @FXML private void goToPlaylists()  { accueilController.navigateTo("/view/playlist.fxml"); }
    @FXML private void goToHistorique() { accueilController.navigateTo("/view/historique.fxml"); }
    @FXML private void goToAdmin()      { accueilController.navigateTo("/view/administrateur.fxml"); }

    @FXML
    private void handleLogout() {
        try {
            AppContext.getInstance().setAbonneConnecte(null);
            AppContext.getInstance().setRole(null);
            Parent root = FXMLLoader.load(getClass().getResource("/view/bienvenue.fxml"));
            Stage stage = (Stage) roleLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 800));
        } catch (Exception e) { e.printStackTrace(); }
    }
}