package controller.view_controllers;

import controller.UserController;
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

import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.mockito.Mockito.*;

class AdminUsersControllerTest extends ApplicationTest {

    private static UserController userController;
    private static AdminUsersController adminUsersController;
    private Parent root;
    private Stage stage;
    private final List<User> testUsers = seedTestDB();

    @Override
    public void start(Stage stage) throws Exception {
        when(userController.displayAllUsers()).thenReturn(testUsers);
        this.stage = stage;

        var fxmlLocation = getClass().getResource("/fxml/admin_users.fxml");
        assertNotNull(fxmlLocation, "admin_users.fxml file not found.");

        ResourceBundle testBundle = ResourceBundle.getBundle("Messages", Locale.US);
        FXMLLoader loader = getFxmlLoader(fxmlLocation);
        loader.setResources(testBundle);

        root = loader.load();
        adminUsersController = loader.getController();

        if (adminUsersController != null) {
            adminUsersController.setStage(stage);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private static FXMLLoader getFxmlLoader(URL fxmlLocation) {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> {
            if (param == AdminUsersController.class) {
                AdminUsersController controller = new AdminUsersController();
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
    }

    @BeforeEach
    void setUp() {
        assertNotNull(adminUsersController, "Controller should be initialized");
    }

    @Test
    void testNameClick() {
        verifyThat("#userNameLabel", Node::isVisible);
        clickOn("#userNameLabel");
        verifyThat(".alert", isVisible());
    }

    @Test
    void testDeleteUser() {
        verifyThat("#deleteIcon", Node::isVisible);
        int initialSize = adminUsersController.getUsersGrid().getChildren().size();
        clickOn("#deleteIcon");
        verifyThat(".alert", isVisible());
        clickOn(ButtonType.OK.getText());
        int newSize = adminUsersController.getUsersGrid().getChildren().size();
        assertEquals(initialSize - 1, newSize, "Grid should have shrunk by 1");
    }

    private static List<User> seedTestDB() {
        List<User> testUserList = new ArrayList<>();
        User user1 = new User("Alice", "password1", "alice@example.com", "dummy", "1234567890",new Date(), "en");

        User user2 = new User("Bob", "password2", "bob@example.com", "dummy", "0987654321",new Date(), "en");

        User user3 = new User("Charlie", "password3", "charlie@example.com", "dummy", "1122334455", new Date(), "en");

        User user4 = new User("Agatha", "password4", "agatha@example.com", "dummy", "23232323211", new Date(), "en");

        testUserList.add(user1);
        testUserList.add(user2);
        testUserList.add(user3);
        testUserList.add(user4);
        return testUserList;
    }
}