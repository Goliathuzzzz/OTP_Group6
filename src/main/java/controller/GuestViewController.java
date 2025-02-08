package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Guest;

import java.util.Date;

public class GuestViewController extends BaseController {

    GuestController gController = new GuestController();

    @FXML
    private TextField phoneField;

    @FXML
    private Button continueButton;

    @FXML
    private ImageView backIcon1, homeIcon, profileIcon, backIcon;

    @FXML
    private void handleContinueClick() {
        String phoneNumber = phoneField.getText();
        if (phoneNumber.isEmpty()) {
            showAlert( Alert.AlertType.INFORMATION, "syötä","syötä puhelinnumero.");
        } else {
            gController.registerGuest(new Guest(phoneNumber, new Date()));
            switchScene("begin_session");
        }
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        //showAlert( Alert.AlertType.INFORMATION, "kotiin","siirrytään etusivulle");
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        switchScene("options");
    }
}
