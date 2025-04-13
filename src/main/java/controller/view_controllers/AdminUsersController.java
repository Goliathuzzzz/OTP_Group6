package controller.view_controllers;

import controller.BaseController;
import controller.UserController;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

public class AdminUsersController extends BaseController implements Initializable {

    private final ResourceBundle bundle = localeManager.getBundle();
    private UserController userController = new UserController();
    private List<User> users;
    @FXML
    private GridPane usersGrid;
    @FXML
    private AnchorPane adminUsersPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        users = userController.displayAllUsers(); // fetch users from database
        populateUserGrid();
        Platform.runLater(() -> {
            Stage stage = (Stage) adminUsersPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("admin_users"));
            } else {
                System.out.println("Stage is null in AdminUsersController initialize()");
            }
        });
    }

    // populate the grid pane with user items
    private void populateUserGrid() {
        usersGrid.getChildren().clear();

        int columns = 2; // determine how many columns we want to have
        int row = 0;
        int column = 0;

        for (User user : users) {
            Node userItemNode = createUserItemNode(user); // create a user item UI node
            if (userItemNode != null) {
                usersGrid.add(userItemNode, column,
                        row); // add the user item to the grid pane at the position

                // move to the next cell
                if (++column == columns) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    // creates a user item node for the user
    private Node createUserItemNode(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(AdminUsersController.class.getResource("/fxml/user_item.fxml"));
            Node userItemNode = loader.load();

            // get the controller for user and set the user
            UserItemController controller = loader.getController();
            controller.setUser(user, this::handleUserDeleted);

            // store the controller in the user item node
            userItemNode.setUserData(controller);
            return userItemNode;
        } catch (IOException e) {
            System.err.println("Error loading user item: " + e.getMessage());
            return null;
        }
    }

    // calls the user controller to delete the user
    private void handleUserDeleted(User user) {
        userController.deleteUser(user);
        users.remove(user);
        removeUserFromGrid(user);
    }

    // remove the user from the UI grid
    private void removeUserFromGrid(User user) {
        // find the user item node that corresponds to the user
        Node nodeToRemove = usersGrid.getChildren().stream().filter(node -> {
            UserItemController controller = (UserItemController) node.getUserData();
            // check if the user in the controller matches the user we want to remove
            return controller != null && controller.getUser().equals(user);
        }).findFirst().orElse(null);
        // remove the node from the grid and refresh the grid
        if (nodeToRemove != null) {
            usersGrid.getChildren().remove(nodeToRemove);
            refreshGrid();
        }
    }

    // refresh the grid after a user has been removed to prevent empty spaces
    private void refreshGrid() {
        int columns = 2;
        int row = 0;
        int column = 0;

        for (Node node : usersGrid.getChildren()) {
            GridPane.setColumnIndex(node, column);
            GridPane.setRowIndex(node, row);
            if (++column == columns) {
                column = 0;
                row++;
            }
        }
    }

    // For testing
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    // For testing
    public GridPane getUsersGrid() {
        return usersGrid;
    }
}
