package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import context.GuiContext;
import controller.viewControllers.LoginController;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

public class LoginControllerTest extends ApplicationTest {


    private static UserController userController;
    private static LoginController controller;
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);
    private Parent root;
    private final GuiContext guiContext = GuiContext.getInstance();

    @BeforeAll
    static void start() {
        userController = Mockito.mock(UserController.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/fxml/login.fxml");
        assertNotNull(fxmlLocation, "Login.fxml file not found.");
        FXMLLoader loader = new FXMLLoader(fxmlLocation, bundle);
        root = loader.load();
        controller = loader.getController();
        controller.setUserController(userController);
        guiContext.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        assertNotNull(controller, "Controller should be initialized");
    }

    // use seeder if this test fails
    @Test
    void testValidLoginSwitchesScene() {
        verifyThat("#emailField", Node::isVisible);
        User user = new User("alice", "password1", "alice@example.com", "dummy", "1234567890",
                new Date(), "en");
        when(userController.login("alice@example.com", "password1")).thenReturn(user);
        clickOn("#emailField").write("alice@example.com");
        clickOn("#passwordField").write("password1");
        clickOn("#loginButton");
        Stage stage = guiContext.getStage();
        Parent newRoot = stage.getScene().getRoot();

        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#beginSessionPane"), "Begin Session scene should be loaded.");
        assertNotSame(newRoot, root, "Scene root should have changed after login.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals(bundle.getString("begin"), stage.getTitle(),
                "Stage title should match after scene switch.");
    }

    @Test
    void testNewAccountSwitchesScene() {
        clickOn("#newAccount");
        Stage stage = guiContext.getStage();
        Parent newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        assertNotNull(newRoot.lookup("#registrationPane"), "Registration scene should be loaded.");
        assertNotSame(newRoot, root, "Scene root should have changed after clicking New Account.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals(bundle.getString("registration"), stage.getTitle(),
                "Stage title should match after scene switch.");
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
