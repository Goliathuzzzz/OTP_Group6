package controller.view_controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

import gui_context.GuiContext;
import controller.UserController;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

class EditProfileControllerTest extends ApplicationTest {

    private static UserController userController;
    private static EditProfileController editProfileController;
    private static GuiContext context;
    private static User user;
    private static String name;
    private static String email;
    private static String phone;
    private Parent root;
    private Stage stage;

    private static FXMLLoader getFxmlLoader(URL fxmlLocation) {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> {
            if (param == EditProfileController.class) {
                EditProfileController controller = new EditProfileController();
                controller.setUserController(userController);
                return controller;
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return loader;
    }

    @BeforeAll
    static void start() {
        userController = Mockito.mock(UserController.class);
        context = GuiContext.getInstance();
        name = "Marko";
        email = "marko@email.com";
        phone = "0500332331";
        user = new User(name, "markopassword", email, "dummy", phone, new Date(), "en");
        context.setUser(user);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        var fxmlLocation = getClass().getResource("/fxml/edit_profile.fxml");
        assertNotNull(fxmlLocation, "edit_profile.fxml file not found.");

        ResourceBundle testBundle = ResourceBundle.getBundle("Messages", Locale.US);
        FXMLLoader loader = getFxmlLoader(fxmlLocation);
        loader.setResources(testBundle);

        root = loader.load();
        editProfileController = loader.getController();

        if (editProfileController != null) {
            editProfileController.setStage(stage);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        assertNotNull(editProfileController, "Controller should be initialized");
    }

    @Test
    void testProfileEdit() {
        verifyThat("#nameLabel", isVisible());
        Node nameField = lookup("#nameField").query();
        Node emailField = lookup("#emailField").query();
        Node phoneField = lookup("#phoneField").query();
        clickOn(nameField);
        eraseText(name.length());
        verifyThat(nameField, hasText(""));
        clickOn(nameField).write("heta");
        clickOn(emailField);
        eraseText(email.length());
        verifyThat(emailField, hasText(""));
        clickOn(emailField).write("heta@email.com");
        clickOn(phoneField);
        eraseText(phone.length());
        verifyThat(phoneField, hasText(""));
        clickOn("#phoneField").write("0400356678");
        clickOn("#backIcon1");
        verifyThat(".alert", isVisible());
        clickOn(ButtonType.OK.getText());
        verifyThat("#editButton", isVisible());
        verifyThat("#nameLabel", hasText("heta"));
        verifyThat("#emailLabel", hasText("heta@email.com"));
        verifyThat("#phoneLabel", hasText("0400356678"));
    }
}
