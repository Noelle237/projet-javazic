package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import modele.Artiste;
import modele.Gestionnaire;
import modele.Morceau;
import modele.Album;
import modele.Abonne;
import modele.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private Label statMorceaux;
    @FXML private Label statAlbums;
    @FXML private Label statArtistes;
    @FXML private Label statAbonnes;
    @FXML private TableView<Object> tableCatalogue;
    @FXML private TableColumn<Object, String> colType;
    @FXML private TableColumn<Object, String> colNom;
    @FXML private TableColumn<Object, String> colInfo;
    @FXML private TableColumn<Object, String> colActions;
    @FXML private TableView<Utilisateur> tableAbonnes;
    @FXML private TableColumn<Utilisateur, String> colUsername;
    @FXML private TableColumn<Utilisateur, String> colStatut;
    @FXML private TableColumn<Utilisateur, String> colDateInscription;
    @FXML private TableColumn<Utilisateur, String> colActionsUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rafraichir();
    }

    private void rafraichir() {
        Gestionnaire g = Gestionnaire.getInstance();

        statMorceaux.setText(String.valueOf(g.getCatalogue().getMorceaux().size()));
        statAlbums.setText(String.valueOf(g.getCatalogue().getAlbums().size()));
        statArtistes.setText(String.valueOf(g.getCatalogue().getArtistes().size()));
        long nbAbonnes = g.getUtilisateurs().stream().filter(u -> u instanceof Abonne).count();
        statAbonnes.setText(String.valueOf(nbAbonnes));

        colType.setCellValueFactory(data -> {
            Object obj = data.getValue();
            String type = obj instanceof Morceau ? "Morceau" : obj instanceof Album ? "Album" : "Artiste";
            return new javafx.beans.property.SimpleStringProperty(type);
        });
        colNom.setCellValueFactory(data -> {
            Object obj = data.getValue();
            String nom = obj instanceof Morceau ? ((Morceau) obj).getTitre()
                       : obj instanceof Album ? ((Album) obj).getTitre()
                       : ((Artiste) obj).getNom();
            return new javafx.beans.property.SimpleStringProperty(nom);
        });
        colInfo.setCellValueFactory(data -> {
            Object obj = data.getValue();
            String info = obj instanceof Morceau ? ((Morceau) obj).getArtiste().getNom()
                        : obj instanceof Album ? String.valueOf(((Album) obj).getAnnee())
                        : "";
            return new javafx.beans.property.SimpleStringProperty(info);
        });

        ObservableList<Object> catalogue = FXCollections.observableArrayList();
        catalogue.addAll(g.getCatalogue().getMorceaux());
        catalogue.addAll(g.getCatalogue().getAlbums());
        catalogue.addAll(g.getCatalogue().getArtistes());
        tableCatalogue.setItems(catalogue);

        colUsername.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getIdentifiant()));
        colStatut.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getClass().getSimpleName()));
        colDateInscription.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        tableAbonnes.setItems(FXCollections.observableArrayList(g.getUtilisateurs()));
    }

    @FXML
    private void handleAjouterMorceau() {
        if (Gestionnaire.getInstance().getCatalogue().getArtistes().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Ajoutez d'abord un artiste.", ButtonType.OK).showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un morceau");
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField titre = new TextField(); titre.setPromptText("Titre");
        TextField genre = new TextField(); genre.setPromptText("Genre");
        ComboBox<String> artiste = new ComboBox<>();
        Gestionnaire.getInstance().getCatalogue().getArtistes()
            .forEach(a -> artiste.getItems().add(a.getNom()));
        artiste.getSelectionModel().selectFirst();

        // Sélecteur de fichier audio
        TextField cheminFichier = new TextField(); 
        cheminFichier.setPromptText("Chemin du fichier audio");
        cheminFichier.setEditable(false);
        Button btnChoisir = new Button("Parcourir...");
        btnChoisir.setOnAction(e -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Choisir un fichier audio");
            fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Fichiers audio", "*.mp3", "*.wav", "*.ogg", "*.aac")
            );
            java.io.File fichier = fileChooser.showOpenDialog(statMorceaux.getScene().getWindow());
            if (fichier != null) {
                cheminFichier.setText(fichier.toURI().toString());
                // Auto-remplir le titre avec le nom du fichier
                if (titre.getText().isEmpty()) {
                    String nom = fichier.getName().replaceAll("\\.[^.]+$", "");
                    titre.setText(nom);
                }
            }
        });

        HBox fichierBox = new HBox(8, cheminFichier, btnChoisir);

        grid.add(new Label("Titre:"), 0, 0); grid.add(titre, 1, 0);
        grid.add(new Label("Genre:"), 0, 1); grid.add(genre, 1, 1);
        grid.add(new Label("Artiste:"), 0, 2); grid.add(artiste, 1, 2);
        grid.add(new Label("Fichier audio:"), 0, 3); grid.add(fichierBox, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK && !titre.getText().isEmpty()) {
                Artiste a = Gestionnaire.getInstance().getCatalogue().getArtistes().stream()
                    .filter(ar -> ar.getNom().equals(artiste.getValue())).findFirst().orElse(null);
                Morceau m = new Morceau(titre.getText(), 0, genre.getText(), null, a);
                m.setCheminFichier(cheminFichier.getText());
                Gestionnaire.getInstance().getCatalogue().ajouterMorceau(m);
                Gestionnaire.getInstance().sauvegarder();
                rafraichir();
            }
        });
    }
    @FXML
    private void handleAjouterAlbum() {
        if (Gestionnaire.getInstance().getCatalogue().getArtistes().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Ajoutez d'abord un artiste.", ButtonType.OK).showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un album");
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        TextField titre = new TextField(); titre.setPromptText("Titre");
        TextField annee = new TextField(); annee.setPromptText("Année");
        TextField genre = new TextField(); genre.setPromptText("Genre");
        ComboBox<String> artiste = new ComboBox<>();
        Gestionnaire.getInstance().getCatalogue().getArtistes()
            .forEach(a -> artiste.getItems().add(a.getNom()));
        artiste.getSelectionModel().selectFirst();
        grid.add(new Label("Titre:"), 0, 0); grid.add(titre, 1, 0);
        grid.add(new Label("Année:"), 0, 1); grid.add(annee, 1, 1);
        grid.add(new Label("Genre:"), 0, 2); grid.add(genre, 1, 2);
        grid.add(new Label("Artiste:"), 0, 3); grid.add(artiste, 1, 3);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK && !titre.getText().isEmpty()) {
                Artiste a = Gestionnaire.getInstance().getCatalogue().getArtistes().stream()
                    .filter(ar -> ar.getNom().equals(artiste.getValue())).findFirst().orElse(null);
                int an = 2024;
                try { an = Integer.parseInt(annee.getText()); } catch (Exception ignored) {}
                Album alb = new Album(titre.getText(), an, a, genre.getText(), "");
                Gestionnaire.getInstance().getCatalogue().ajouterAlbum(alb);
                Gestionnaire.getInstance().sauvegarder();
                rafraichir();
            }
        });
    }

    @FXML
    private void handleAjouterArtiste() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un artiste");
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        TextField nom = new TextField(); nom.setPromptText("Nom");
        TextField bio = new TextField(); bio.setPromptText("Biographie");
        grid.add(new Label("Nom:"), 0, 0); grid.add(nom, 1, 0);
        grid.add(new Label("Bio:"), 0, 1); grid.add(bio, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK && !nom.getText().isEmpty()) {
                Artiste a = new Artiste(nom.getText(), bio.getText());
                Gestionnaire.getInstance().getCatalogue().ajouterArtiste(a);
                Gestionnaire.getInstance().sauvegarder();
                rafraichir();
            }
        });
    }

    @FXML
    private void handleDeconnexion() {
        AppContext.getInstance().setModeConnexion(null);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/bienvenue.fxml"));
            Stage stage = (Stage) statMorceaux.getScene().getWindow();
            stage.setScene(new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}