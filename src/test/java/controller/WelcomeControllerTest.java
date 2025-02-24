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

import static org.junit.jupiter.api.Assertions.*;

public class WelcomeControllerTest extends ApplicationTest {

    private WelcomeController controller;
    private Parent root;
    private Stage stage;

    @BeforeAll
    public static void setupHeadless() {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
    }


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
        sleep(500);
        Parent newRoot = stage.getScene().getRoot();

        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#optionsPane"), "Options.fxml should be loaded and contain optionsPane.");
        assertNotSame(newRoot, root, "Scene root should have changed after handleClick.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals("päävalikko", stage.getTitle(), "Stage title should match after scene switch.");
    }
}
