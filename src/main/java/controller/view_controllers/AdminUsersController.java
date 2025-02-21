package controller.view_controllers;

import controller.BaseController;
import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.User;
import util.SceneNames;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminUsersController extends BaseController implements Initializable {

    private final UserController userController = new UserController();

    @FXML
    private GridPane usersGrid;

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
        List<User> users = userController.displayAllUsers(); // fetch users from database
        populateUserGrid(users);
    }

    // populate the grid pane with user items
    private void populateUserGrid(List<User> users) {
        usersGrid.getChildren().clear();

        int columns = 2; // determine how many columns we want to have
        int row = 0;
        int column = 0;

        for (User user : users) {
            Node userItemNode = createUserItemNode(user); // create a user item UI node
            if (userItemNode != null) {
                usersGrid.add(userItemNode, column, row); // add the user item to the grid pane at the position

                // move to the next cell
                column++;
                if (column == columns) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    // creates a user item node for the user
    private Node createUserItemNode(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user_item.fxml"));
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
        List<Node> children = new ArrayList<>(usersGrid.getChildren());
        populateUserGrid(extractUsersFromChildren(children));
    }

    // extract users from the user item nodes
    private List<User> extractUsersFromChildren(List<Node> children) {
        List<User> users = new ArrayList<>();
        for (Node node : children) {
            UserItemController controller = (UserItemController) node.getUserData();
            if (controller != null) {
                users.add(controller.getUser());
            }
        }
        return users;
    }
}
