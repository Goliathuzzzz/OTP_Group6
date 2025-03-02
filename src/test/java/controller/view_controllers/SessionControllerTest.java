package controller.view_controllers;

import context.GUIContext;
import controller.MatchController;

import controller.UserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.*;
import model.categories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.net.URL;
import java.util.*;

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
    private static UserController userController;
    private static Matcher matcher;
    private static User user;
    private static List<User> userList;

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
                return new SessionController();
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
        guiContext = GUIContext.getInstance();
        user = new User("alicia", "password1", "alicia@example.com", "dummy", "1234567890",new Date());
        session = new Session(user);
        guiContext.setUser(user);
        guiContext.setSession(session);
    }

    @BeforeEach
    void setUp() {
        assertNotNull(sessionController, "Controller should be initialized");
        session.getParticipantInterests().clear();
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
    void testSelectedInterestsAreSaved() {
        session = new Session(user);
        guiContext.setSession(session);
        Participant participant = session.getParticipant();
        assertNotNull(participant, "Participant should not be null.");
        participant.clearInterests();
        assertTrue(participant.getInterests().isEmpty(), "Participant should have no interests.");

        verifyThat("#interestsContainer", isVisible());

        VBox interestsContainer = lookup("#interestsContainer").query();
        assertFalse(interestsContainer.getChildren().isEmpty(), "There should be available interests to select.");

        Node selectedCategory = interestsContainer.getChildren().getFirst();
        clickOn(selectedCategory);

        verifyThat("#optionsContainer", isVisible());

        VBox optionsContainer = lookup("#optionsContainer").query();
        assertFalse(optionsContainer.getChildren().isEmpty(), "There should be available options to select.");

        Pane firstOptionPane = (Pane) optionsContainer.getChildren().getFirst();
        RadioButton selectedOption = (RadioButton) firstOptionPane.getChildren().stream()
                .filter(node -> node instanceof RadioButton)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No radio buttons found in options pane."));
        clickOn(selectedOption);
        assertTrue(selectedOption.isSelected(), "Option should be selected.");

        assertFalse(session.getParticipantInterests().isEmpty(), "There should be selected interests.");

        Category selectedInterest = session.getParticipantInterests().getFirst();
        System.out.println("Selected interest: " + selectedInterest);

        assertNotNull(participant, "Participant should not be null.");
        assertTrue(participant.getInterests().contains(selectedInterest), "Participant should have selected interest.");
    }

    @Test
    void testSessionWithNoSelectedInterests() {
        session = new Session(user);
        guiContext.setSession(session);
        verifyThat("#readyButton", isVisible());
        clickOn("#readyButton");
        verifyThat(".alert", isVisible());
    }

//    @Test
//    void testFullSession() {
//        Participant participant = session.getParticipant();
//        System.out.println("Session participant: " + participant);
//        assertNotNull(participant, "Participant should not be null.");
//
//        participant.clearInterests();
//        assertTrue(participant.getInterests().isEmpty(), "Participant should have no interests.");
//
//        verifyThat("#interestsContainer", isVisible());
//
//        VBox interestsContainer = lookup("#interestsContainer").query();
//        assertFalse(interestsContainer.getChildren().isEmpty(), "There should be available interests to select.");
//
//        Node selectedCategory = interestsContainer.getChildren().getFirst();
//        clickOn(selectedCategory);
//
//        verifyThat("#optionsContainer", isVisible());
//
//        VBox optionsContainer = lookup("#optionsContainer").query();
//        assertFalse(optionsContainer.getChildren().isEmpty(), "There should be available options to select.");
//
//        Pane firstOptionPane = (Pane) optionsContainer.getChildren().getFirst();
//        RadioButton selectedOption = (RadioButton) firstOptionPane.getChildren().stream()
//                .filter(node -> node instanceof RadioButton)
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("No radio buttons found in options pane."));
//        clickOn(selectedOption);
//        assertTrue(selectedOption.isSelected(), "Option should be selected.");
//
//        verifyThat("#goBack", isVisible());
//        clickOn("#goBack");
//
//        assertFalse(session.getParticipantInterests().isEmpty(), "There should be selected interests.");
//        Category selectedInterest = session.getParticipantInterests().getFirst();
//        System.out.println("Selected interest: " + selectedInterest);
//
//        assertTrue(participant.getInterests().contains(selectedInterest), "Participant should have selected interest.");
//        HashMap<User, Double> topMatches = getMatches();
//        when(matcher.getTopMatches()).thenReturn(topMatches);
//        sessionController.setMatcher(matcher);
//        verifyThat("#readyButton", isVisible());
//        clickOn("#readyButton");
//        verify(matcher, times(1)).getTopMatches();
//        if (guiContext.getMatches() == null || guiContext.getMatches().isEmpty()) {
//            fail("There should be matches.");
//        }
//        verifyThat("#matchPane", Node::isVisible);
//        verifyThat("#matchHeart", isVisible());
//        verifyThat("#detailsButton", isVisible());
//        clickOn("#detailsButton");
//
//        verifyThat("#afterMatchPane", isVisible());
//    }

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