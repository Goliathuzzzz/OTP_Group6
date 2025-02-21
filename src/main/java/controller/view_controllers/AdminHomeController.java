package controller.view_controllers;

import controller.BaseController;
import controller.MatchController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.Participant;
import util.SceneNames;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminHomeController extends BaseController implements Initializable {

    private final MatchController matchController = new MatchController();

    @FXML
    private VBox pairsContainer;

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
    private void handlePreviousClick(MouseEvent mouseEvent) {
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    private void handleHelpClick(MouseEvent mouseEvent) {
        System.out.println("help");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // fetch pairs from database (currently dummy data)
        List<Pair<Participant, Participant>> pairs = fetchPairsFromDatabase();

        for (Pair<Participant, Participant> pair : pairs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pair_item.fxml"));
                Node pairNode = loader.load();

                // get the controller for the pair item and set the names
                PairItemController pairController = loader.getController();
                pairController.setParticipants(pair.getKey(), pair.getValue());

                // add the loaded pair item to the container
                pairsContainer.getChildren().add(pairNode);
            } catch (IOException e) {
                System.err.println("Error loading pair item: " + e.getMessage());
            }
        }
    }

    private List<Pair<Participant, Participant>> fetchPairsFromDatabase() {
        return matchController.fetchPairsFromDatabase();
    }
}
