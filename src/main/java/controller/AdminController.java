package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController extends BaseController implements Initializable {

    @FXML
    private VBox pairsContainer;

    @FXML
    private void handleHomeClick(MouseEvent event) {
        // showAlert(Alert.AlertType.INFORMATION, "kotiin","siirrytään etusivulle");
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        // showAlert(Alert.AlertType.INFORMATION, "profiiliin","siirrytään profiiliin");
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        // showAlert(Alert.AlertType.INFORMATION, "takaisin", "siirrytään kirjautumiseen");
        switchScene("options");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // fetch pairs from database (currently dummy data)
        List<Pair<String, String>> pairs = fetchPairsFromDatabase();

        for (Pair<String, String> pair : pairs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pair_item.fxml"));
                Node pairNode = loader.load();

                // get the controller for the pair item and set the names
                PairItemController pairController = loader.getController();
                pairController.setNames(pair.getKey(), pair.getValue());

                // add the loaded pair item to the container
                pairsContainer.getChildren().add(pairNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // replace this with database fetching logic
    private List<Pair<String, String>> fetchPairsFromDatabase() {
        List<Pair<String, String>> pairs = new ArrayList<>();
        pairs.add(new Pair<>("heta", "mikael"));
        pairs.add(new Pair<>("pitkänimi...", "mikael"));
        pairs.add(new Pair<>("sami", "simo"));
        pairs.add(new Pair<>("mikael", "sami"));
        pairs.add(new Pair<>("simo", "heta"));
        pairs.add(new Pair<>("sami", "simo"));
        pairs.add(new Pair<>("sami", "simo"));
        return pairs;
    }
}
