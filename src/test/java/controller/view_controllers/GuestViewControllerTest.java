package controller.view_controllers;

import controller.BaseController;
import controller.GuestController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

class GuestViewControllerTest extends ApplicationTest {
    private static GuestController guestController;
    private static GuestViewController guestViewController;
    private Parent root;
    private Stage stage;
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);

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


    @BeforeAll
    static void start() {
        guestController = Mockito.mock(GuestController.class);
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
        assertEquals(bundle.getString("begin"), stage.getTitle(), "Stage title should match after scene switch.");
    }

    @Test
    void testNoPhoneNumber() {
        verifyThat("#phoneField", isVisible());
        clickOn("#continueButton");
        verifyThat(".alert", isVisible());
    }

}