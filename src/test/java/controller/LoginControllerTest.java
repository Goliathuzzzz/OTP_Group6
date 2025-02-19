package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class LoginControllerTest extends ApplicationTest {

    private LoginController controller;
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        var fxmlLocation = getClass().getResource("/fxml/login.fxml");
        assertNotNull(fxmlLocation, "Login.fxml file not found.");

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
    void testValidLoginSwitchesScene() {
        clickOn("#emailField").write("alice@example.com");
        clickOn("#passwordField").write("password1");
        clickOn("#loginButton");

        Parent newRoot = stage.getScene().getRoot();

        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#beginSessionPane"), "Begin Session scene should be loaded.");
        assertNotSame(newRoot, root, "Scene root should have changed after login.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals("aloita", stage.getTitle(), "Stage title should match after scene switch.");
    }

    @Test
    void testNewAccountSwitchesScene() {
        clickOn("#newAccount");
        Parent newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#registrationPane"), "Registration scene should be loaded.");
        assertNotSame(newRoot, root, "Scene root should have changed after clicking New Account.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals("rekisteröinti", stage.getTitle(), "Stage title should match after scene switch.");
    }

    @Test
    void testForgotPasswordShowsAlert() {
        clickOn("#forgotPassword");
        verifyThat(".alert", isVisible());
    }

    @Test
    void testEmptyFieldsShowError() {
        clickOn("#loginButton");
        verifyThat(".alert", isVisible());
    }

    @Test
    void testInvalidLoginShowsError() {
        clickOn("#emailField").write("invalid@example.com");
        clickOn("#passwordField").write("wrongpassword");
        clickOn("#loginButton");
        verifyThat(".alert", isVisible());
    }
}
