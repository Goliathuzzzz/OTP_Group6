package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class RegistrationControllerTest extends ApplicationTest {

    private RegistrationController controller;
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        var fxmlLocation = getClass().getResource("/fxml/registration.fxml");
        assertNotNull(fxmlLocation, "Registration.fxml file not found.");

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
    void testSuccessfulRegistration() {
        clickOn("#emailField").write("newuser@example.com");
        clickOn("#phoneField").write("1234567890");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password123");
        clickOn("#signupButton");

        Parent newRoot = stage.getScene().getRoot();
        assertNotNull(newRoot.lookup("#beginSessionPane"), "Begin session scene should be loaded.");
        assertTrue(stage.isShowing(), "Stage should still be showing after scene switch.");
        assertEquals("aloita", stage.getTitle(), "Stage title should be updated after registration.");
    }

    @Test
    void testEmptyFieldsShowError() {
        clickOn("#signupButton");
        verifyThat(".alert", isVisible());
    }

    @Test
    void testPasswordMismatchShowsError() {
        clickOn("#emailField").write("user@example.com");
        clickOn("#phoneField").write("1234567890");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password456");
        clickOn("#signupButton");

        verifyThat(".alert", isVisible());
    }

    @Test
    void testInvalidEmailShowsError() {
        clickOn("#emailField").write("invalid-email");
        clickOn("#phoneField").write("1234567890");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password123");
        clickOn("#signupButton");

        verifyThat(".alert", isVisible());
    }

    @Test
    void testInvalidPhoneShowsError() {
        clickOn("#emailField").write("user@example.com");
        clickOn("#phoneField").write("abcde12345");
        clickOn("#passwordField").write("password123");
        clickOn("#confirmPasswordField").write("password123");
        clickOn("#signupButton");

        verifyThat(".alert", isVisible());
    }

    @Test
    void testDuplicateEmailShowsError() {
        UserController userController = new UserController();
        if (!userController.existsByEmail("existing@example.com")) {
            User existingUser = new User();
            existingUser.setEmail("existing@example.com");
            existingUser.setPassword("password123");
            existingUser.setUserName("Existing User");
            existingUser.setJoinDate(new java.util.Date());
            existingUser.setPhoneNumber("1234567890");
            existingUser.setRole("USER");
            userController.registerUser(existingUser);
        }

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
        assertNotNull(newRoot.lookup("#optionsPane"), "Options scene should be loaded after navigating back.");
        assertEquals("päävalikko", stage.getTitle(), "Stage title should be updated after navigating back.");
    }
}
