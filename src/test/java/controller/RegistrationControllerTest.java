package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import controller.view_controllers.RegistrationController;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

public class RegistrationControllerTest extends ApplicationTest {

    private static UserController userController;
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);
    private RegistrationController controller;
    private Parent root;
    private Stage stage;

    @BeforeAll
    static void starter() {
        userController = Mockito.mock(UserController.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        URL fxmlLocation = getClass().getResource("/fxml/registration.fxml");
        assertNotNull(fxmlLocation, "Registration.fxml file not found.");
        FXMLLoader loader = new FXMLLoader(fxmlLocation, bundle);
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


    @BeforeEach
    void setUp() {
        assertNotNull(controller, "Controller should be initialized");
    }

    @Test
    void testSuccessfulRegistration() {
        verifyThat("#emailField", Node::isVisible);
        clickOn("#emailField").write("newuser@example.com");
        clickOn("#phoneField").write("1234567890");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password123");
        clickOn("#signupButton");


        Parent newRoot = stage.getScene().getRoot();
        assertNotNull(newRoot.lookup("#beginSessionPane"), "Begin session scene should be loaded.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals(bundle.getString("begin"), stage.getTitle(),
                "Stage title should be updated after registration.");
    }

    @Test
    void testEmptyFieldsShowError() {
        clickOn("#signupButton");

        verifyThat(".alert", isVisible());
    }

    @Test
    void testPasswordMismatchShowsError() {
        verifyThat("#emailField", Node::isVisible);
        clickOn("#emailField").write("user@example.com");
        clickOn("#phoneField").write("1234567890");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password456");
        clickOn("#signupButton");
        verifyThat(".alert", isVisible());
    }

    @Test
    void testInvalidEmailShowsError() {
        verifyThat("#emailField", Node::isVisible);
        clickOn("#emailField").write("invalid-email");
        clickOn("#phoneField").write("1234567890");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password123");
        clickOn("#signupButton");
        verifyThat(".alert", isVisible());
    }

    @Test
    void testInvalidPhoneShowsError() {
        verifyThat("#emailField", Node::isVisible);
        clickOn("#emailField").write("user@example.com");
        clickOn("#phoneField").write("abcde12345");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password123");
        clickOn("#signupButton");

        verifyThat(".alert", isVisible());
    }

    @Test
    void testDuplicateEmailShowsError() {
        verifyThat("#emailField", Node::isVisible);
        when(userController.existsByEmail("existing@example.com")).thenReturn(true);
        clickOn("#emailField").write("existing@example.com");
        clickOn("#phoneField").write("1234567890");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password123");
        clickOn("#signupButton");

        verifyThat(".alert", isVisible());
    }

    @Test
    void testBackNavigation() {
        Parent root = stage.getScene().getRoot();
        assertNotNull(root, "The initial scene should be loaded.");

        Node backIcon = root.lookup("#backIcon");
        assertNotNull(backIcon, "backIcon should be present in the scene.");
        clickOn(backIcon);

        Parent newRoot = stage.getScene().getRoot();
        assertNotNull(newRoot.lookup("#optionsPane"),
                "Options scene should be loaded after navigating back.");
        assertEquals(bundle.getString("main_menu"), stage.getTitle(),
                "Stage title should be updated after navigating back.");
    }
}
