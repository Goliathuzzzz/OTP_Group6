package controller.viewControllers;

import context.GuiContext;
import context.LocaleManager;
import controller.BaseController;
import controller.GuestController;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Guest;
import util.SceneNames;

public class GuestViewController extends BaseController {

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();
    GuestController gController = new GuestController();
    @FXML
    private TextField phoneField;
    @FXML
    private AnchorPane guestPane;
    @FXML
    private Button continueButton;
    @FXML
    private ImageView backIcon1, homeIcon, profileIcon, backIcon;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) guestPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("guest"));
            } else {
                System.err.println("Stage is null in GuestViewController initialize()");
            }
        });
    }

    @FXML
    private void handleContinueClick() {
        String phoneNumber = phoneField.getText();
        if (phoneNumber.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, bundle.getString("input_title"),
                    bundle.getString("enter_phone_prompt"));
        } else if (!isValidPhone(phoneNumber)) {
            showAlert(Alert.AlertType.INFORMATION, bundle.getString("error_title"),
                    bundle.getString("invalid_phone"));
        } else {
            Guest guest = new Guest(phoneNumber, new Date());
            gController.registerGuest(guest);
            GuiContext.getInstance().setGuest(guest);
            switchScene(SceneNames.BEGIN_SESSION);
        }
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,15}$");
    }

    public void setGuestViewController(GuestController guestController) {
        this.gController = guestController;
    }
}
