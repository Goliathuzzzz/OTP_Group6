package controller.view_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import guiContext.GuiContext;
import controller.MatchController;
import controller.UserController;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;
import model.Matcher;
import model.Participant;
import model.Session;
import model.User;
import model.categories.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class SessionControllerTest extends ApplicationTest {

    private static SessionController sessionController;
    private static GuiContext guiContext;
    private static Session session;
    private static MatchController matchController;
    private static UserController userController;
    private static Matcher matcher;
    private static User user;
    private static List<User> userList;
    private Parent root;

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
        guiContext = GuiContext.getInstance();
        user = new User("alicia", "password1", "alicia@example.com", "dummy", "1234567890",
                new Date(), "en");
        session = new Session(user);
        guiContext.setUser(user);
        guiContext.setSession(session);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/fxml/session.fxml");
        assertNotNull(fxmlLocation, "session.fxml file not found.");
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlLocation);
        loader.setResources(bundle);
        root = loader.load();
        sessionController = loader.getController();
        guiContext.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        Parent newRoot = guiContext.getStage().getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#continueButton", Node::isVisible);
        clickOn("#continueButton");
        verifyThat("#optionsContainer", Node::isVisible);
    }

    @Test
    void testSelectedInterestsAreSavedAndRemoved() {
        session = new Session(user);
        guiContext.setSession(session);
        Participant participant = session.getParticipant();
        assertNotNull(participant, "Participant should not be null.");
        participant.clearInterests();
        assertTrue(participant.getInterests().isEmpty(), "Participant should have no interests.");

        verifyThat("#interestsContainer", isVisible());

        VBox interestsContainer = lookup("#interestsContainer").query();
        assertFalse(interestsContainer.getChildren().isEmpty(),
                "There should be available interests to select.");

        Node selectedCategory = interestsContainer.getChildren().getFirst();
        clickOn(selectedCategory);

        verifyThat("#optionsContainer", isVisible());

        VBox optionsContainer = lookup("#optionsContainer").query();
        assertFalse(optionsContainer.getChildren().isEmpty(),
                "There should be available options to select.");

        Pane firstOptionPane = (Pane) optionsContainer.getChildren().getFirst();
        RadioButton selectedOption = (RadioButton) firstOptionPane.getChildren().stream()
                .filter(node -> node instanceof RadioButton)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("No radio buttons found in options pane."));
        clickOn(selectedOption);
        assertTrue(selectedOption.isSelected(), "Option should be selected.");

        assertFalse(session.getParticipantInterests().isEmpty(),
                "There should be selected interests.");

        Category selectedInterest = session.getParticipantInterests().getFirst();
        System.out.println("Selected interest: " + selectedInterest);

        assertNotNull(participant, "Participant should not be null.");
        assertTrue(participant.getInterests().contains(selectedInterest),
                "Participant should have selected interest.");

        clickOn(selectedOption);
        assertFalse(selectedOption.isSelected(), "Option should be unselected.");

        assertFalse(session.getParticipantInterests().contains(selectedInterest),
                "Interest should be removed from session.");
        assertFalse(participant.getInterests().contains(selectedInterest),
                "Interest should be removed from participant.");
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
        Parent newRoot = guiContext.getStage().getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#optionsContainer", isVisible());
        verifyThat("#goBack", isVisible());
        clickOn("#goBack");
        newRoot = guiContext.getStage().getScene().getRoot();
        System.out.println("New scene root id: " + newRoot.getId());
        verifyThat("#readyButton", isVisible());
    }

    @Test
    void testMatching() {
        matchController = mock(MatchController.class);
        matcher = mock(Matcher.class);
        sessionController.setMatchController(matchController);
        sessionController.setMatcher(matcher);

        session = new Session(user);
        guiContext.setSession(session);
        Participant participant = session.getParticipant();

        assertNotNull(participant, "Participant should not be null.");

        User match1 =
                new User("match1", "pass", "match1@example.com", "dummy", "1234567890", new Date(),
                        "en");
        User match2 =
                new User("match2", "pass", "match2@example.com", "dummy", "0987654321", new Date(),
                        "en");

        HashMap<User, Double> topMatches = new HashMap<>();
        topMatches.put(match1, 95.5);
        topMatches.put(match2, 89.3);

        when(matcher.getTopMatches()).thenReturn(topMatches);

        sessionController.matchParticipant();

        verify(matchController, times(1)).matchParticipants(participant, match1, 95.5);
        verify(matchController, times(1)).matchParticipants(participant, match2, 89.3);

        List<Match> savedMatches = guiContext.getMatches();
        assertNotNull(savedMatches, "Matches should not be null.");
        assertEquals(2, savedMatches.size(), "There should be two matches.");

        assertTrue(savedMatches.stream().anyMatch(m -> m.getParticipant1().equals(participant)
                        && m.getParticipant2().equals(match1) && m.getCompatibility() == 95.5),
                "Match1 should be stored.");
        assertTrue(savedMatches.stream().anyMatch(m -> m.getParticipant1().equals(participant)
                        && m.getParticipant2().equals(match2) && m.getCompatibility() == 89.3),
                "Match2 should be stored.");
    }
}
