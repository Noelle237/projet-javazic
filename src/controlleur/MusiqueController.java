package controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import modele.Morceau;
import java.net.URL;
import java.util.ResourceBundle;

public class MusiqueController implements Initializable {

    @FXML private Label labelTitre;
    @FXML private Label labelArtiste;
    @FXML private Label labelDuree;
    @FXML private Button btnPlay;
    @FXML private Button btnStop;
    @FXML private Slider sliderVolume;

    private Morceau morceauCourant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        morceauCourant = AppContext.getInstance().getMorceauCourant();
        if (morceauCourant != null) {
            labelTitre.setText(morceauCourant.getTitre());
            labelArtiste.setText(morceauCourant.getArtiste().getNom());
            labelDuree.setText(morceauCourant.getDureeFormatee());
        }
    }

    @FXML
    private void play() {
        btnPlay.setText("▶ En lecture...");
    }

    @FXML
    private void stop() {
        btnPlay.setText("▶ Play");
    }
}