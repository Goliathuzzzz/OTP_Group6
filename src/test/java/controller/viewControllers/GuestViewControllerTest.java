package controller.viewControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import controller.BaseController;
import controller.GuestController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

class GuestViewControllerTest extends ApplicationTest {
    private static GuestController guestController;
    private static GuestViewController guestViewController;
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);
    private Parent root;
    private Stage stage;

    @BeforeAll
    static void start() {
        guestController = Mockito.mock(GuestController.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        var fxmlLocation = getClass().getResource("/fxml/guest.fxml");
        assertNotNull(fxmlLocation, "guest.fxml file not found.");

        FXMLLoader loader = new FXMLLoader(fxmlLocation, bundle);

        root = loader.load();
        guestViewController = loader.getController();
        guestViewController.setGuestViewController(guestController);

        if (guestViewController != null) {
            ((BaseController) guestViewController).setStage(stage);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        assertNotNull(guestViewController, "Controller should be initialized");
    }

    @Test
    void testInvalidPhoneNumber() {
        verifyThat("#phoneField", isVisible());
        clickOn("#phoneField").write("123");
        clickOn("#continueButton");
        verifyThat(".alert", isVisible());
    }

    @Test
    void testValidLoginSwitchesScene() {
        verifyThat("#phoneField", isVisible());
        clickOn("#phoneField").write("1234567890");
        clickOn("#continueButton");

        Parent newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#beginSessionPane"), "Begin Session scene should be loaded.");
        assertNotSame(newRoot, root, "Scene root should have changed after login.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals(bundle.getString("begin"), stage.getTitle(),
                "Stage title should match after scene switch.");
    }

    @Test
    void testNoPhoneNumber() {
        verifyThat("#phoneField", isVisible());
        clickOn("#continueButton");
        verifyThat(".alert", isVisible());
    }

}
