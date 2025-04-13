package controller.view_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import guiContext.LocaleManager;
import controller.MatchController;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

class AdminHomeControllerTest extends ApplicationTest {

    private static MatchController matchController;
    private static AdminHomeController adminHomeController;
    private final List<Match> testMatches = seedTestMatches();
    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();
    private Parent root;
    private Stage stage;

    private static FXMLLoader getFxmlLoader(URL fxmlLocation) {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> {
            if (param == AdminHomeController.class) {
                AdminHomeController controller = new AdminHomeController();
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
    }

    private static List<Match> seedTestMatches() {
        List<Match> testMatchesList = new ArrayList<>();
        User userA = new User("PlayerA", "passA", "playerA@example.com", "user", "1234567890",
                new Date(), "en");
        User userB = new User("PlayerB", "passB", "playerB@example.com", "user", "0987654321",
                new Date(), "en");
        User userC = new User("PlayerC", "passC", "playerC@example.com", "user", "1122334455",
                new Date(), "en");
        User userD = new User("PlayerD", "passD", "playerD@example.com", "user", "2233445566",
                new Date(), "en");
        User userE = new User("PlayerE", "passE", "playerE@example.com", "user", "3344556677",
                new Date(), "en");
        User userF = new User("PlayerF", "passF", "playerF@example.com", "user", "4455667788",
                new Date(), "en");

        testMatchesList.add(new Match(userA, userB, 75.5));
        testMatchesList.add(new Match(userC, userD, 82.3));
        testMatchesList.add(new Match(userE, userF, 90.1));
        testMatchesList.add(new Match(userA, userC, 65.2));
        return testMatchesList;
    }

    @Override
    public void start(Stage stage) throws Exception {
        LocaleManager.getInstance().setLocale(Locale.US);
        when(matchController.displayAllMatches()).thenReturn(testMatches);
        this.stage = stage;

        URL fxmlLocation = getClass().getResource("/fxml/admin_home.fxml");
        assertNotNull(fxmlLocation, "admin_home.fxml file not found.");

        ResourceBundle testBundle = ResourceBundle.getBundle("Messages", Locale.US);
        FXMLLoader loader = getFxmlLoader(fxmlLocation);
        loader.setResources(testBundle);

        root = loader.load();
        adminHomeController = loader.getController();

        if (adminHomeController != null) {
            adminHomeController.initialize(null, testBundle);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        assertNotNull(adminHomeController, "Controller should be initialized");
    }

    @Test
    void testMatchListPopulation() {
        VBox pairsContainer = lookup("#pairsContainer").query();
        assertNotNull(pairsContainer, "Pairs container should exist");
        assertEquals(testMatches.size(),
                adminHomeController.getPairsContainer().getChildren().size(),
                "Should display all matches");
    }

    @Test
    void testDeleteMatch() {
        int initialSize = adminHomeController.getPairsContainer().getChildren().size();
        Node firstMatchNode = adminHomeController.getPairsContainer().getChildren().get(0);
        clickOn(firstMatchNode);
        verifyThat(".alert", isVisible());
        clickOn("OK");
        int newSize = adminHomeController.getPairsContainer().getChildren().size();
        assertEquals(initialSize - 1, newSize, "Match should be removed");
    }
}
