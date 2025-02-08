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

    // Event handler for "Valmis" button
    @FXML
    private void handleValmis(ActionEvent event) {
        System.out.println("Valmis button clicked!");
        // Implement logic when the "Valmis" button is pressed (e.g., save selections, move to another scene)
    }

    // Navigation Handlers
    @FXML
    private void handleHomeClick(MouseEvent event) {
        navigateTo(event, "/fxml/home.fxml");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        navigateTo(event, "/fxml/profile.fxml");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {

    }

    @FXML
    public void handleInterestClick(Pane interestPane) {
        interestsContainer.getChildren().forEach(node -> node.getStyleClass().remove("selected"));
        interestPane.getStyleClass().add("selected");
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
