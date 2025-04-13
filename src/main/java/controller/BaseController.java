package controller;

import gui_context.GuiContext;
import gui_context.LocaleManager;
import controller.view_controllers.InterestSelectionController;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.SceneNames;

// Common methods for all controllers which interact with the GUI
public abstract class BaseController {

    protected Stage stage;
    protected GuestController guestController = new GuestController();
    protected LocaleManager localeManager = LocaleManager.getInstance();
    protected GuiContext guiContext = GuiContext.getInstance();
    protected final Logger logger = Logger.getLogger("logger");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    protected void switchScene(String destination) {
        switchScene(destination, null); //call overloaded method with no data for most cases
    }

    protected void switchScene(String destination, Object data) {
        GuiContext context = GuiContext.getInstance();

        if (SceneNames.PROFILE.equals(destination)) {
            destination = handleProfileSwitch(context, destination);
            if (destination == null) {
                return;
            }
        }
        if (SceneNames.OPTIONS.equals(destination)) {
            handleOptionsSwitch(context);
        }

        loadFxml(destination, data);
    }

    private void loadFxml(String destination, Object data) {
        String path = "/fxml/" + destination + ".fxml";
        logger.log(Level.INFO, "Loading FXML: {}", path);
        ResourceBundle bundle;
        try {
            bundle = localeManager.getBundle();
        } catch (MissingResourceException e) {
            logger.info("ERROR: ResourceBundle is invalid in BaseController.switchScene. "
                    + "Defaulting to en_US");
            bundle = ResourceBundle.getBundle("Messages",
                    new Locale.Builder().setLanguage("en").setRegion("US").build());
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BaseController.class.getResource(path));
            fxmlLoader.setResources(bundle);
            Parent root = fxmlLoader.load();
            BaseController controller = fxmlLoader.getController();
            Stage stage1 = guiContext.getStage();

            if (controller != null) {
                controller.setStage(stage1);
            } else {
                logError("Controller is null in fxmlLoader");
            }

            if (controller instanceof InterestSelectionController interestController &&
                    data instanceof String category) {
                    interestController.setCategory(category);
                    switch (category) {
                        case "animals" -> interestController.loadInterests("animals");
                        case "food" -> interestController.loadInterests("food");
                        case "hobbies" -> interestController.loadInterests("hobbies");
                        case "science" -> interestController.loadInterests("science");
                        case "sports" -> interestController.loadInterests("sports");
                        default -> throw new IllegalArgumentException(
                                "Unexpected category: " + category);
                    }
                }


            if (stage1 == null) {
                logError("Stage is null in guiContext");
                return;
            }
            if (root == null) {
                logError("Root is null from loaded FXML");
                return;
            }
            stage1.setScene(new Scene(root));
            stage1.show();

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
        logger.log(Level.INFO, "ERROR: {}", message);
    }

    private String handleProfileSwitch(GuiContext context, String destination) {
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

    private void handleOptionsSwitch(GuiContext context) {
        if (context.isGuest()) {
            guestController.deleteGuest(context.getGuest());
        }
        context.logout();
    }

    public void handleHomeClick() {
        switchScene(SceneNames.BEGIN_SESSION);
    }

    public void handleProfileClick() {
        switchScene(SceneNames.PROFILE);
    }

    public void handleBackClick() {
        switchScene(SceneNames.OPTIONS);
    }

    public void handlePreviousClick() {
        switchScene(SceneNames.PROFILE);
    }

    public void handleHelpClick() {
        switchScene(SceneNames.HELP);
    }
}
