package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminUsersController extends BaseController implements Initializable {

    @FXML
    private GridPane usersGrid;

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

    public void handlePreviousClick(MouseEvent mouseEvent) {
        switchScene("admin_profile");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // dummy data for users
        List<String> userNames = fetchUserNamesFromDatabase();

        // determine how many columns we want to have
        int columns = 2;
        int row = 0;
        int column = 0;

        for (String userName : userNames) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user_item.fxml"));
                Node userItemNode = loader.load();

                // get the controller for user and set the name
                UserItemController controller = loader.getController();
                controller.setUserName(userName);

                // add the user item to the grid pane
                usersGrid.add(userItemNode, column, row);

                column++;
                if (column == columns) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // replace this with database fetching logic
    private List<String> fetchUserNamesFromDatabase() {
        List<String> names = new ArrayList<>();
        names.add("kalle");
        names.add("liisa");
        names.add("matti");
        names.add("anna");
        names.add("pekka");
        names.add("sanna");
        names.add("juha");
        names.add("mika");
        names.add("kalle");
        names.add("liisa");
        names.add("matti");
        names.add("anna");
        names.add("pekka");
        names.add("sanna");
        names.add("juha");
        names.add("mika");
        names.add("kalle");
        names.add("liisa");
        names.add("matti");
        names.add("anna");
        names.add("pekka");
        names.add("sanna");
        names.add("juha");
        names.add("mika");
        names.add("kalle");
        names.add("liisa");
        names.add("matti");
        names.add("anna");
        names.add("pekka");
        names.add("sanna");
        names.add("juha");
        names.add("mika");
        return names;
    }
}
