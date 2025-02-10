package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class SessionController extends BaseController {

    @FXML
    private Button valmisButton;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private VBox interestsContainer;

    @FXML
    private Label titleLabel, subtitleLabel;

    @FXML
    private void handleReady(ActionEvent event) {
        System.out.println("valmis painettu.");
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "kotiin", "siirrytään etusivulle");
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "profiiliin", "siirrytään profiiliin");
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION,"takaisin", "siirrytään kirjautumiseen");
        switchScene("options");
    }

    @FXML
    public void initialize() {
        for (Node interest : interestsContainer.getChildren()) {
            interest.setOnMouseClicked(this::handleInterestSelection);
        }
    }

    private void handleInterestSelection(MouseEvent event) {
        Pane selectedInterest = (Pane) event.getSource();

        if (selectedInterest.getStyleClass().contains("selected")) {
            selectedInterest.getStyleClass().remove("selected");
        } else {
            selectedInterest.getStyleClass().add("selected");
        }
    }

    // Utility function to change scenes
    private void navigateTo(MouseEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading " + fxmlFile);
        }
    }
}
