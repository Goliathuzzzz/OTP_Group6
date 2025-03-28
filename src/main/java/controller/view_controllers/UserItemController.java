package controller.view_controllers;

import context.LocaleManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.User;

import java.util.ResourceBundle;
import java.util.function.Consumer;

public class UserItemController {

    @FXML
    public ImageView deleteIcon;

    @FXML
    private Label userNameLabel;

    private User user;
    private Consumer<User> onDelete;
    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();

    public void setUser(User user, Consumer<User> onDelete) {
        this.user = user;
        this.onDelete = onDelete;
        userNameLabel.setText(user.getUserName());
    }

    public User getUser() {
        return user;
    }

    @FXML
    private void handleUserNameClick(MouseEvent event) {
        if (user == null) {
            System.err.println("ERROR: User is null in UserItemController");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("click_name_alert"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("click_name_confirmation") + user.getUserName());
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteClick(MouseEvent event) {
        if (user == null) {
            System.err.println("User is null in UserItemController");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("user_removal_alert"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("confirm_user_removal") + user.getUserName() + "?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteUser();
            }
        });
    }

    private void deleteUser() {
        // notify AdminUsersController that user should be deleted
        if (user != null) {
            onDelete.accept(user);
        } else {
            System.err.println("ERROR: User is null in UserItemController");
        }
    }
}
