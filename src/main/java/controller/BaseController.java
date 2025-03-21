package controller;

import context.GUIContext;
import controller.view_controllers.InterestSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.SceneNames;
import java.io.IOException;

// Common methods for all controllers which interact with the GUI
public abstract class BaseController {

    GuestController guestController = new GuestController();
    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    protected void switchScene(String destination) {
        switchScene(destination, null); //call overloaded method with no data for most cases
    }

    protected void switchScene(String destination, Object data) {
        GUIContext context = GUIContext.getInstance();

        if (destination.equals(SceneNames.PROFILE)) {
            destination = handleProfileSwitch(context, destination);
            if (destination == null) {
                return;
            }
        }
        if (destination.equals(SceneNames.OPTIONS)) {
            handleOptionsSwitch(context);
        }

        loadFXML(destination, data);
    }

    private void loadFXML(String destination, Object data) {
        String path = "/fxml/" + destination + ".fxml";

        try {
            System.out.println("DEBUG: Loading FXML from " + path);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = fxmlLoader.load();

            BaseController controller = fxmlLoader.getController();

            if (controller != null) {
                controller.setStage(this.stage);
            } else {
                logError("Controller is null in BaseController.switchScene");
            }

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

            if (this.stage != null) {
                this.stage.setScene(new Scene(root));
                this.stage.show();
            } else {
                logError("Stage is null in BaseController.switchScene");
            }

        } catch (IOException e) {
            logError("Failed to load FXML: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "error_title", "cannot_load_view");
        }
    }

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void logError(String message) {
        System.err.println("ERROR: " + message);
    }

    private String handleProfileSwitch(GUIContext context, String destination) {
        if (!context.isUser() && !context.isGuest()) {
            logError("No user data found in BaseController.switchScene");
            showAlert(Alert.AlertType.ERROR, "error_title", "no_user_data");
            return null;
        }
        if (context.isAdmin()) {
            destination = SceneNames.ADMIN_PROFILE; // redirect to admin profile
        }
        return destination;
    }

    private void handleOptionsSwitch(GUIContext context) {
        if (context.isGuest()) {
            guestController.deleteGuest(context.getGuest());
        }
        context.logout();
    }

    public void handleHomeClick(MouseEvent e) {
        switchScene(SceneNames.BEGIN_SESSION);
    }

    public void handleProfileClick(MouseEvent e) {
        switchScene(SceneNames.PROFILE);
    }

    public void handleBackClick(MouseEvent e) {
        switchScene(SceneNames.OPTIONS);
    }

    public void handlePreviousClick(MouseEvent e) {
        switchScene(SceneNames.PROFILE);
    }

    public void handleHelpClick(MouseEvent e) {
        switchScene(SceneNames.HELP);
    }
}
