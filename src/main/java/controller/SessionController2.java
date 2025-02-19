package controller;

import context.GUIContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import model.*;
import util.SceneNames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionController2 extends BaseController {

    private final GUIContext context = GUIContext.getInstance();
    private Session session;
    private Participant participant;
    private MatchController matchController;
    private Matcher matcher;

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
        matchParticipant();
        System.out.println("valmis painettu.");
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        switchScene(SceneNames.OPTIONS);
    }

    @FXML
    public void initialize() {
        for (Node interest : interestsContainer.getChildren()) {
            interest.setOnMouseClicked(this::handleInterestSelection);
        }
        // needs to wait for stage to be set
        Platform.runLater(() -> {
            System.out.println("DEBUG: stage is " + (stage == null ? "NULL" : "SET"));
            session = context.getSession();
            if (session == null) {
                System.err.println("ERROR: session is null in SessionController2");
                return;
            }
            participant = session.getParticipant();
            matchController = new MatchController();
            matcher = new Matcher(session);
        });
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

    private void matchParticipant() {
        matcher.matchParticipant();
        List<Match> matches = new ArrayList<>();
        HashMap<User, Double> topMatches = matcher.getTopMatches();
        for (Map.Entry<User, Double> entry: topMatches.entrySet()) {
            matchController.matchParticipants(participant, entry.getKey(), entry.getValue());
            matches.add(new Match(participant, entry.getKey(), entry.getValue()));
        }
        context.setMatches(matches);
    }
}