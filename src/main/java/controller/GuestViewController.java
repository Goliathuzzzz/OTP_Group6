package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GuestViewController extends BaseController {

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
            //showAlert( Alert.AlertType.INFORMATION, "jatketaan","jatketaan vierailijana puhelinnumerolla: " + phoneNumber);
            switchScene("profile");
        }
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        showAlert( Alert.AlertType.INFORMATION, "kotiin","siirrytään etusivulle");
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
