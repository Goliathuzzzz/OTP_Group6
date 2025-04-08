package controller.view_controllers;

import context.LocaleManager;
import controller.BaseController;
import controller.MatchController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;

public class AdminHomeController extends BaseController implements Initializable {

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();
    private MatchController matchController = new MatchController();
    @FXML
    private VBox pairsContainer;
    @FXML
    private AnchorPane adminHomePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Match> matches = matchController.displayAllMatches();
        populateMatchList(matches);
        Platform.runLater(() -> {
            Stage stage = (Stage) adminHomePane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("admin_users"));
            } else {
                System.out.println("Stage is null in AdminUsersController initialize()");
            }
        });
    }

    private void populateMatchList(List<Match> matches) {
        pairsContainer.getChildren().clear();
        for (Match match : matches) {
            Node matchNode = createMatchItemNode(match);
            if (matchNode != null) {
                pairsContainer.getChildren().add(matchNode);
            }
        }
    }

    private Node createMatchItemNode(Match match) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pair_item.fxml"));
            Node matchNode = loader.load();
            PairItemController controller = loader.getController();
            controller.setMatch(match, this::deleteMatch);
            matchNode.setUserData(controller);
            return matchNode;
        } catch (IOException e) {
            System.err.println("ERROR: Could not load pair item: " + e.getMessage());
            return null;
        }
    }

    private void deleteMatch(Match match) {
        matchController.deleteMatch(match);
        removeMatchFromList(match);
    }

    private void removeMatchFromList(Match match) {
        pairsContainer.getChildren().stream()
                .filter(node -> {
                    PairItemController controller = (PairItemController) node.getUserData();
                    return controller != null && controller.getMatch().equals(match);
                })
                .findFirst()
                .ifPresent(nodeToRemove -> pairsContainer.getChildren().remove(nodeToRemove));
    }

    // testing purposes
    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
    }

    // testing purposes
    public VBox getPairsContainer() {
        return pairsContainer;
    }
}
