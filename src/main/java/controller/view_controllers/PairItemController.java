package controller.view_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Match;

import java.util.function.Consumer;

public class PairItemController {
    @FXML private Label nameLabel1;
    @FXML private Label nameLabel2;

    private Match match;
    private Consumer<Match> onDelete;

    public void setMatch(Match match, Consumer<Match> onDelete) {
        this.match = match;
        this.onDelete = onDelete;
        nameLabel1.setText(match.getParticipant1().getDisplayName());
        nameLabel2.setText(match.getParticipant2().getDisplayName());
    }

    public Match getMatch() {
        return match;
    }

    @FXML
    private void handleMatchClick(MouseEvent event) {
        if (match == null) {
            System.err.println("ERROR: Match is null in PairItemController.handleMatchClick");
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("parin poisto");
        alert.setHeaderText(null);
        alert.setContentText("oletko varma että haluat poistaa parin " + match.getParticipant1().getDisplayName() + " ja " + match.getParticipant2().getDisplayName() + "?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deletePair();
            }
        });
    }

    private void deletePair() {
        if (match != null && onDelete != null) {
            onDelete.accept(match);
        }
    }
}
