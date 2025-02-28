package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import model.Match;
import model.categories.Category;
import util.MatchUtils;
import java.util.List;


public class AfterMatchController extends BaseController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private Label percentageLabel, matchParticipantsLabel, interestsLabel;

    @FXML
    private AnchorPane afterMatchPane;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) afterMatchPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Sport");
            } else {
                System.out.println("Stage is null in AfterMatchController initialize()");
            }
            setResults();
        });
    }

    private void setResults() {
        GUIContext context = GUIContext.getInstance();
        List<Match> matches = context.getMatches();
        if (matches == null) {
            System.err.println("ERROR: Match is null in AfterMatchController setResults()");
        }
        else {
            Match match = matches.getLast();
            matchParticipantsLabel.setText(match.getParticipant1().getDisplayName() + " ja " + match.getParticipant2().getDisplayName());
            percentageLabel.setText(Math.round(match.getCompatibility()) + "%");
            List<Category> interests = MatchUtils.findCommonInterests(match.getParticipant1().getInterests(), match.getParticipant2().getInterests());
            interestsLabel.setText(interests.isEmpty() ? "ei yhteisiä kiinnostuksenkohteita" : MatchUtils.formatInterests(interests));
        }
    }
}
