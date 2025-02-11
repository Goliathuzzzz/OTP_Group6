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

    protected void switchScene(String destination) {
        switchScene(destination, null); //call overloaded method with no data for most cases
    }

    protected void switchScene(String destination, Object data) {
        String path = "/fxml/" + destination + ".fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = fxmlLoader.load();

            BaseController controller = fxmlLoader.getController();
            controller.setStage(stage);

            //pass the data only if applicable (InterestSelectionController)
            if (data != null && controller instanceof InterestSelectionController interestController) {
                if (data instanceof String category) {
                    interestController.setCategory(category);
                    switch (category) {
                        case "animals" -> interestController.loadInterests("animals");
                        case "food" -> interestController.loadInterests("food");
                        case "hobbies" -> interestController.loadInterests("hobbies");
                        case "science" -> interestController.loadInterests("science");
                        case "sports" -> interestController.loadInterests("sports");
                    }
                }
            }
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
}
