package controller.view_controllers;

import context.GuiContext;
import context.LocaleManager;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.User;

public class UserItemController {

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();
    private final GuiContext guiContext = GuiContext.getInstance();
    @FXML
    public ImageView deleteIcon;
    @FXML
    private Label userNameLabel;
    private User user;
    private Consumer<User> onDelete;

    public void setUser(User user, Consumer<User> onDelete) {
        this.user = user;
        this.onDelete = onDelete;
        String language = guiContext.getLanguage();
        String userName;
        if (user.getLocalization(language).isPresent()) {
            userName = user.getLocalization(language).get().getUserName();
        } else {
            userName = user.getAnyUserName();
        }
        userNameLabel.setText(userName);
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
        alert.setContentText(bundle.getString("click_name_confirmation") + userNameLabel.getText());
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
        alert.setContentText(
                bundle.getString("confirm_user_removal") + userNameLabel.getText() + "?");
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
