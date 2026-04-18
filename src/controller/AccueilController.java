package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import model.Abonne;

public class AccueilController {

    @FXML private StackPane contentArea;
    @FXML private BarreLateraleController barreLateraleController;
    @FXML private MusiqueController musiqueController;

    private String role;
    private Abonne abonne;

    public void setRole(String role) {
        this.role = role;
        AppContext.getInstance().setRole(role);
        if (barreLateraleController != null) {
            barreLateraleController.setRole(role, this);
        }
        navigateTo("/view/catalogue.fxml");
    }

    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
        AppContext.getInstance().setAbonneConnecte(abonne);
    }

    public void navigateTo(String fxmlPath) {
        try {
            Node vue = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(vue);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public MusiqueController getMusiqueController() {
        return musiqueController;
    }
}