package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BeginSessionController extends BaseController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private void initialize() {
        //System.out.println("BeginSessionController initialized.");
        System.out.println("DEBUG: stage is " + (stage == null ? "NULL" : "SET"));

    }

    @FXML
    private void handleBeginSessionClick(MouseEvent event) {
        // showAlert(Alert.AlertType.INFORMATION, "aloitetaan", "siirrytään valintoihin");
        switchScene("session");
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        //showAlert( Alert.AlertType.INFORMATION, "kotiin","siirrytään etusivulle");
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        //showAlert( Alert.AlertType.INFORMATION, "profiiliin","siirrytään profiiliin");
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "takaisin", "siirrytään kirjautumiseen");
        switchScene("options");
    }
}

