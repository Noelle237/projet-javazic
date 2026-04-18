package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BienvenueController {

    @FXML
    private void handleAdmin(ActionEvent e) {
        ouvrirAccueil("admin", e);
    }

    @FXML
    private void handleClient(ActionEvent e) {
        ouvrirAccueil("abonne", e);
    }

    @FXML
    private void handleVisiteur(ActionEvent e) {
        ouvrirAccueil("visiteur", e);
    }

    @FXML
    private void handleCreer(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/authentification.fxml"));
            Parent root = loader.load();
            AuthentificationController ctrl = loader.getController();
            ctrl.setMode("creer");
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 800));
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @FXML
    private void handleQuitter(ActionEvent e) {
        System.exit(0);
    }

    private void ouvrirAccueil(String role, ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/accueil.fxml"));
            Parent root = loader.load();
            AccueilController ctrl = loader.getController();
            ctrl.setRole(role);
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 800));
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}