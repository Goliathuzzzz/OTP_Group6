package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UserItemController {

    @FXML
    public ImageView deleteIcon;

    @FXML
    private Label userNameLabel;

    public void setUserName(String userName) {
        userNameLabel.setText(userName);
    }

    @FXML
    private void handleUserNameClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("nimen klikkaus");
        alert.setHeaderText(null);
        alert.setContentText("klikkasit nimeä " + userNameLabel.getText());
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("käyttäjän poisto");
        alert.setHeaderText(null);
        alert.setContentText("poistit käyttäjän " + userNameLabel.getText());
        alert.showAndWait();
    }
}
