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

import java.io.IOException;
import java.util.Map;

public class SessionController2 extends BaseController {

    @FXML
    private Button valmisButton;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private VBox interestsContainer;

    @FXML
    private Label titleLabel, subtitleLabel;

    private static final Map<String, String> CATEGORY_MAP = Map.of(
            "eläimet", "animals",
            "ruoka", "food",
            "harrastukset", "hobbies",
            "urheilu", "sports",
            "tiede", "science"
    );

    @FXML
    private void handleReady(ActionEvent event) {
        System.out.println("valmis painettu.");
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

    @FXML
    public void initialize() {
        for (Node interest : interestsContainer.getChildren()) {
            interest.setOnMouseClicked(this::handleInterestSelection);
        }
        System.out.println("DEBUG: stage is " + (stage == null ? "NULL" : "SET"));
    }

    @FXML
    private void handleInterestSelection(MouseEvent event) {
        Pane selectedInterest = (Pane) event.getSource();
        if (selectedInterest.getStyleClass().contains("selected")) {
            selectedInterest.getStyleClass().remove("selected");
        } else {
            selectedInterest.getStyleClass().add("selected");
        }

        Label interestLabel = (Label) selectedInterest.lookup(".interest-label");
        if (interestLabel != null) {
            String categoryKey = interestLabel.getText().toLowerCase();
            if (CATEGORY_MAP.containsKey(categoryKey)) {
                navigateToInterests(event, CATEGORY_MAP.get(categoryKey));
            }
        }
    }
    private void navigateToInterests(MouseEvent event, String category) {
        switchScene("interest_selection", category); //passing category only for interest selection
        System.out.println("Navigated to category: " + category);
    }
}