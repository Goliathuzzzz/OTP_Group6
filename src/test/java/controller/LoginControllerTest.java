package controller;

import controller.view_controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.mockito.Mockito.*;

public class LoginControllerTest extends ApplicationTest {

    @Mock
    private static UserController userController;

    private static LoginController controller;
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
        controller.setUserController(userController);

        if (controller != null) {
            ((BaseController) controller).setStage(stage);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @BeforeAll
    static void start() {
        userController = Mockito.mock(UserController.class);
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
    }
    @BeforeEach
    void setUp() {
        assertNotNull(controller, "Controller should be initialized");
    }

    // use seeder if this test fails
    @Test
    void testValidLoginSwitchesScene() {
        verifyThat("#emailField", Node::isVisible);
        User user = new User("alice", "password1", "alice@example.com", "dummy", "1234567890",new Date());
        when(userController.login("alice@example.com", "password1")).thenReturn(user);
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
    void testInvalidLoginShowsError() {
        verifyThat("#emailField", Node::isVisible);
        clickOn("#emailField").write("invalid@example.com");
        clickOn("#passwordField").write("wrongpassword");
        clickOn("#loginButton");
        verifyThat(".alert", isVisible());
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


}
