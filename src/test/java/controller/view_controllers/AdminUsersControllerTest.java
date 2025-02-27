package controller.view_controllers;

import controller.UserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.PointQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        root = loader.load();
        adminUsersController = loader.getController();
        adminUsersController.setUserController(userController);

        if (adminUsersController != null) {
            adminUsersController.setStage(stage);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        User user1 = new User("Alice", "password1", "alice@example.com", "dummy", "1234567890",new Date());

        User user2 = new User("Bob", "password2", "bob@example.com", "dummy", "0987654321",new Date());

        User user3 = new User("Charlie", "password3", "charlie@example.com", "dummy", "1122334455", new Date());

        User user4 = new User("Agatha", "password4", "agatha@example.com", "dummy", "23232323211", new Date());

        testUserList.add(user1);
        testUserList.add(user2);
        testUserList.add(user3);
        testUserList.add(user4);
        return testUserList;
    }
}