package controller.view_controllers;

import context.GUIContext;
import controller.MatchController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.Participant;
import model.Session;
import model.User;
import model.categories.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import java.net.URL;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.mockito.Mockito.*;

class SessionControllerTest extends ApplicationTest {


    private static SessionController sessionController;
    private static GUIContext guiContext;
    private Parent root;
    private Stage stage;

    private static Session session;
    private static MatchController matchController;
    private static User user;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        var fxmlLocation = getClass().getResource("/fxml/session.fxml");
        assertNotNull(fxmlLocation, "session.fxml file not found.");

        FXMLLoader loader = getFxmlLoader(fxmlLocation);
        root = loader.load();
        sessionController = loader.getController();

        if (sessionController != null) {
            sessionController.setStage(stage);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static FXMLLoader getFxmlLoader(URL fxmlLocation) {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> {
            if (param == SessionController.class) {
                SessionController controller = new SessionController();
                controller.setMatchController(matchController);
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
        matchController = Mockito.mock(MatchController.class);
        guiContext = GUIContext.getInstance();
        user = new User("alice", "password1", "alice@example.com", "dummy", "1234567890",new Date());
        session = new Session(user);
        guiContext.setSession(session);
    }

    @BeforeEach
    void setUp() {
        assertNotNull(sessionController, "Controller should be initialized");
    }

    @Test
    void testCategoryClick() {
        verifyThat("#interestsContainer", Node::isVisible);
        VBox interestsContainer = lookup("#interestsContainer").query();
        clickOn(interestsContainer.getChildren().getFirst());
        Parent newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#continueButton", Node::isVisible);
    }

    @Test
    void testSessionWithNoSelectedInterests() {
        verifyThat("#readyButton", isVisible());
        clickOn("#readyButton");
        Parent newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#matchHeart", isVisible());
        verifyThat("#detailsButton", isVisible());
        clickOn("#detailsButton");
        newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#afterMatchPane", isVisible());
        assertNull(guiContext.getMatches(), "There should be no matches.");
    }

    @Test
    void testFullSession() {
        verifyThat("#readyButton", isVisible());
        VBox interestsContainer = lookup("#interestsContainer").query();
        clickOn(interestsContainer.getChildren().getFirst());
        Parent newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#optionsContainer", isVisible());
        VBox optionsContainer = lookup("#optionsContainer").query();
        verifyThat("#continueButton", isVisible());
        for (int i = 0; i < sessionController.getCategoryMap().size(); i++) {
            clickOn(optionsContainer.getChildren().getFirst());
            clickOn("#continueButton");
        }
        newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#readyButton", isVisible());
        clickOn("#readyButton");
        newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#matchHeart", isVisible());
        verifyThat("#detailsButton", isVisible());
        clickOn("#detailsButton");
        newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#afterMatchPane", isVisible());
    }

    @Test
    void testBackButtonInInterestSelection() {
        verifyThat("#readyButton", isVisible());
        VBox interestsContainer = lookup("#interestsContainer").query();
        clickOn(interestsContainer.getChildren().getFirst());
        Parent newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#optionsContainer", isVisible());
        verifyThat("#goBack", isVisible());
        clickOn("#goBack");
        newRoot = stage.getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#readyButton", isVisible());
    }
}