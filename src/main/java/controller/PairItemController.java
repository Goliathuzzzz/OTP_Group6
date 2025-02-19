package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Participant;

public class PairItemController {
    @FXML private Label nameLabel1;
    @FXML private Label nameLabel2;
    private Participant part1;
    private Participant part2;

    public void setParticipants(Participant part1, Participant part2) {
        this.part1 = part1;
        this.part2 = part2;
        nameLabel1.setText(part1.getDisplayName());
        nameLabel2.setText(part2.getDisplayName());
    }

    @FXML
    private void handleName1Click(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("nimen klikkaus");
        alert.setHeaderText(null);
        alert.setContentText("klikkasit nimeä " + part1.getDisplayName());
        alert.showAndWait();
    }

    @FXML
    private void handleName2Click(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("nimen klikkaus");
        alert.setHeaderText(null);
        alert.setContentText("klikkasit nimeä " + part2.getDisplayName());
        alert.showAndWait();
    }

    @FXML
    private void handleMatchClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("parihistorian nouto");
        alert.setHeaderText(null);
        alert.setContentText("noudetaan parihistoriaa: " + part1.getDisplayName() + " x " + part2.getDisplayName());
        alert.showAndWait();
    }
}
