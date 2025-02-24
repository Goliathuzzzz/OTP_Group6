package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import controller.GuestController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Guest;
import util.SceneNames;

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
        }
        else if (!isValidPhone(phoneNumber)) {
            showAlert( Alert.AlertType.INFORMATION, "virhe", "puhelinnumero ei ole kelvollinen.");
        } else {
            Guest guest = new Guest(phoneNumber, new Date());
            gController.registerGuest(guest);
            GUIContext.getInstance().setGuest(guest);
            switchScene(SceneNames.BEGIN_SESSION);
        }
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,15}$");
    }
}
