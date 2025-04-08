package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.view_controllers.WelcomeController;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class WelcomeControllerTest extends ApplicationTest {

    ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);
    private WelcomeController controller;
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        URL fxmlLocation = getClass().getResource("/fxml/welcome.fxml");
        assertNotNull(fxmlLocation, "Welcome.fxml file not found.");
        FXMLLoader loader = new FXMLLoader(fxmlLocation, bundle);
        root = loader.load();
        controller = loader.getController();
        if (controller != null) {
            ((BaseController) controller).setStage(stage);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        assertNotNull(controller, "Controller should be initialized");
    }

    @Test
    void testHandleClickSwitchesScene() {
        clickOn(root);
        sleep(500);
        Parent newRoot = stage.getScene().getRoot();

        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#languagePane"),
                "Language.fxml should be loaded and contain languagePane.");
        assertNotSame(newRoot, root, "Scene root should have changed after handleClick.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals(bundle.getString("language_selection"), stage.getTitle(),
                "Stage title should match after scene switch.");
    }
}
