package controller;

import controller.view_controllers.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class WelcomeControllerTest extends ApplicationTest {

    private WelcomeController controller;
    private Parent root;
    private Stage stage;
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);

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
        assertNotNull(newRoot.lookup("#languagePane"), "Language.fxml should be loaded and contain languagePane.");
        assertNotSame(newRoot, root, "Scene root should have changed after handleClick.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals(bundle.getString("language_selection"), stage.getTitle(), "Stage title should match after scene switch.");
    }
}
