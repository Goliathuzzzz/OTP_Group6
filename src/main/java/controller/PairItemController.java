package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PairItemController {
    @FXML private Label nameLabel1;
    @FXML private Label nameLabel2;

    public void setNames(String name1, String name2) {
        nameLabel1.setText(name1);
        nameLabel2.setText(name2);
    }

    @FXML
    private void handleName1Click(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("nimen klikkaus");
        alert.setHeaderText(null);
        alert.setContentText("klikkasit nimeä " + nameLabel1.getText());
        alert.showAndWait();
    }

    @FXML
    private void handleName2Click(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("nimen klikkaus");
        alert.setHeaderText(null);
        alert.setContentText("klikkasit nimeä " + nameLabel2.getText());
        alert.showAndWait();
    }

    @FXML
    private void handleMatchClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("parihistorian nouto");
        alert.setHeaderText(null);
        alert.setContentText("noudetaan parihistoriaa: " + nameLabel1.getText() + " x " + nameLabel2.getText());
        alert.showAndWait();
    }
}
