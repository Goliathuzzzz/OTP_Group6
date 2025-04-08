package controller.view_controllers;

import context.GUIContext;
import context.LocaleManager;
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
import java.util.Locale;
import java.util.ResourceBundle;


public class AfterMatchController extends BaseController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private Label percentageLabel, matchParticipantsLabel, interestsLabel;

    @FXML
    private AnchorPane afterMatchPane;

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) afterMatchPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("outcome"));
            } else {
                System.out.println("Stage is null in AfterMatchController initialize()");
            }
            setResults();
        });
    }

    private void setResults() {
        GUIContext context = GUIContext.getInstance();
        List<Match> matches = context.getMatches();
        if (matches == null || matches.isEmpty()) {
            System.err.println("ERROR: Match is null in AfterMatchController setResults()");
            // using localized strings for fallback messages
            matchParticipantsLabel.setText(bundle.getString("no_match"));
            percentageLabel.setText("0%");
            interestsLabel.setText(bundle.getString("no_common_interests"));
            return;
        }

        String and = bundle.getString("and");
        Match match = matches.getLast();

        String displayName1 = localizeGuestName(match.getParticipant1().getDisplayName(context.getLanguage()));
        String displayName2 = localizeGuestName(match.getParticipant2().getDisplayName(context.getLanguage()));

        matchParticipantsLabel.setText(displayName1 + " " + and + " " + displayName2);
        percentageLabel.setText(Math.round(match.getCompatibility()) + "%");

        List<Category> interests = MatchUtils.findCommonInterests(
                match.getParticipant1().getInterests(),
                match.getParticipant2().getInterests()
        );

        // localize each shared interest using resource bundle
        if (interests.isEmpty()) {
            interestsLabel.setText(bundle.getString("no_common_interests"));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < interests.size(); i++) {
                Category interest = interests.get(i);
                // dynamically construct the localization key based on enum name
                String key = interest.getClass().getSimpleName().toLowerCase(Locale.ROOT) + "_" + ((Enum<?>) interest).name().toLowerCase(Locale.ROOT);
                sb.append(bundle.getString(key));
                if (i < interests.size() - 1) {
                    sb.append(", ");
                }
            }
            interestsLabel.setText(sb.toString());
        }

    }

    private String localizeGuestName(String name) {
        if (name.startsWith("vieras")) {
            String localizedGuest = bundle.getString("guest");
            return name.replaceFirst("vieras", localizedGuest);
        }
        return name;
    }
}
