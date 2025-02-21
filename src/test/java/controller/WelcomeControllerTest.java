package controller;

import controller.view_controllers.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class WelcomeControllerTest extends ApplicationTest {

    private WelcomeController controller;
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        var fxmlLocation = getClass().getResource("/fxml/welcome.fxml");
        assertNotNull(fxmlLocation, "Welcome.fxml file not found.");

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
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
        Parent newRoot = stage.getScene().getRoot();

        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#optionsPane"), "Options.fxml should be loaded and contain optionsPane.");
        assertNotSame(newRoot, root, "Scene root should have changed after handleClick.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals("päävalikko", stage.getTitle(), "Stage title should match after scene switch.");
    }
}
