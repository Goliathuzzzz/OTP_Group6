package controller.view_controllers;

import context.GuiContext;
import context.LocaleManager;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Match;

public class PairItemController {
    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();
    private final GuiContext context = GuiContext.getInstance();
    @FXML
    private Label nameLabel1;
    @FXML
    private Label nameLabel2;
    private Match match;
    private Consumer<Match> onDelete;

    public void setMatch(Match match, Consumer<Match> onDelete) {
        this.match = match;
        this.onDelete = onDelete;
        nameLabel1.setText(match.getParticipant1().getDisplayName(context.getLanguage()));
        nameLabel2.setText(match.getParticipant2().getDisplayName(context.getLanguage()));
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
        alert.setTitle(bundle.getString("remove_match"));
        alert.setHeaderText(null);

        String and = bundle.getString("and");
        alert.setContentText(bundle.getString("confirm_match_removal") +
                match.getParticipant1().getDisplayName(context.getLanguage()) + " " + and + " "
                + match.getParticipant2().getDisplayName(context.getLanguage()) + "?");
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
