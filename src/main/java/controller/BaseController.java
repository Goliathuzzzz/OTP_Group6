package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

// Common methods for all controllers which interact with the GUI

public abstract class BaseController {
    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    protected void switchScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            BaseController controller = fxmlLoader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to switch scene");
        }
    }

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void handleBarClick(String destination) {
        switch (destination) {
            case "home":
                showAlert(Alert.AlertType.INFORMATION, "kotiin", "siirrytään etusivulle");
                break;
            case "profile":
                switchScene("/profile.fxml");
                break;
            case "back":
                switchScene("/options.fxml");
                break;
            default:
                System.out.println("Unknown destination: " + destination);
        }
    }
}
