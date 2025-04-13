package guiContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import model.Guest;
import model.Match;
import model.Matcher;
import model.Session;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuiContextTest {

    private GuiContext guiContext;
    private User testUser;
    private Guest testGuest;
    private Session testSession;
    private Matcher testMatcher;
    private List<Match> testMatches;

    @BeforeEach
    void setUp() {
        guiContext = GuiContext.getInstance();
        guiContext.logout();
        guiContext.setLanguage("en");

        testUser = new User("Alice", "password", "alice@example.com", "user", "1234567890", null,
                "en");
        testGuest = new Guest("9876543210", null);
        testSession = new Session(testUser);
        testMatcher = new Matcher(testSession);
        testMatches = new ArrayList<>();
    }


    @Test
    void testSingletonInstance() {
        GuiContext instance1 = GuiContext.getInstance();
        GuiContext instance2 = GuiContext.getInstance();
        assertSame(instance1, instance2, "GUIContext should be a singleton.");
    }

    @Test
    void testSetAndGetUser() {
        guiContext.setUser(testUser);
        assertTrue(guiContext.isUser(), "User should be set.");
        assertEquals(testUser, guiContext.getUser(), "Retrieved user should match set user.");
        assertFalse(guiContext.isAdmin(), "Regular user should not be an admin.");
    }

    @Test
    void testSetAndGetAdminUser() {
        User adminUser =
                new User("Admin", "adminpass", "admin@example.com", "admin", "9998887777", null,
                        "en");
        guiContext.setUser(adminUser);
        assertTrue(guiContext.isAdmin(), "Admin flag should be true.");
    }

    @Test
    void testSetAndGetGuest() {
        guiContext.setGuest(testGuest);
        assertTrue(guiContext.isGuest(), "Guest should be set.");
        assertEquals(testGuest, guiContext.getGuest(), "Retrieved guest should match set guest.");
    }

    @Test
    void testSetAndGetUserProperties() {
        guiContext.setUser(testUser);

        guiContext.setUserName("NewAlice");
        guiContext.setUserEmail("newalice@example.com");
        guiContext.setUserPhoneNumber("5556667777");

        assertEquals("NewAlice", guiContext.getUserName());
        assertEquals("newalice@example.com", guiContext.getUserEmail());
        assertEquals("5556667777", guiContext.getUserPhoneNumber());

        assertEquals("NewAlice", guiContext.getUserNameProperty().get());
        assertEquals("newalice@example.com", guiContext.getEmailProperty().get());
        assertEquals("5556667777", guiContext.getPhoneProperty().get());
    }

    @Test
    void testSetAndGetSession() {
        guiContext.setSession(testSession);
        assertEquals(testSession, guiContext.getSession(), "Session should be set correctly.");
    }

    @Test
    void testSetAndGetMatcher() {
        guiContext.setMatcher(testMatcher);
        assertEquals(testMatcher, guiContext.getMatcher(), "Matcher should be set correctly.");
    }

    @Test
    void testSetAndGetMatches() {
        Match match1 = new Match(testUser,
                new User("Bob", "pass", "bob@example.com", "user", "1112223333", null, "en"), 90.5);
        Match match2 = new Match(testUser,
                new User("Charlie", "pass", "charlie@example.com", "user", "4445556666", null,
                        "en"), 85.2);

        testMatches.add(match1);
        testMatches.add(match2);

        guiContext.setMatches(testMatches);
        assertEquals(2, guiContext.getMatches().size(), "Match list should contain 2 matches.");
    }

    @Test
    void testSetMatch() {
        Match match = new Match(testUser,
                new User("David", "pass", "david@example.com", "user", "7778889999", null, "en"),
                78.0);
        guiContext.setMatches(new ArrayList<>());
        guiContext.setMatch(match);

        assertEquals(1, guiContext.getMatches().size(), "Match should be added.");
        assertEquals(match, guiContext.getMatches().getFirst(),
                "Added match should be retrieved correctly.");
    }

    @Test
    void testLogout() {
        guiContext.setUser(testUser);
        guiContext.setGuest(testGuest);
        guiContext.setSession(testSession);
        guiContext.setMatcher(testMatcher);
        guiContext.setMatches(testMatches);

        guiContext.logout();

        assertNull(guiContext.getUser(), "User should be null after logout.");
        assertNull(guiContext.getGuest(), "Guest should be null after logout.");
        assertNull(guiContext.getSession(), "Session should be null after logout.");
        assertNull(guiContext.getMatcher(), "Matcher should be null after logout.");
        assertNull(guiContext.getMatches(), "Matches should be null after logout.");

        assertFalse(guiContext.isUser(), "isUser should be false after logout.");
        assertFalse(guiContext.isGuest(), "isGuest should be false after logout.");
        assertFalse(guiContext.isAdmin(), "isAdmin should be false after logout.");

        assertEquals("", guiContext.getUserNameProperty().get(),
                "UserNameProperty should be cleared.");
        assertEquals("", guiContext.getEmailProperty().get(), "EmailProperty should be cleared.");
        assertEquals("", guiContext.getPhoneProperty().get(), "PhoneProperty should be cleared.");
    }

    @Test
    void testGetId() {
        guiContext.setUser(testUser);
        assertTrue(testUser.getId() >= 0, "User ID should be 0 or a positive number.");
        assertEquals(testUser.getId(), guiContext.getId(),
                "getId() should return the correct user ID.");
        guiContext.logout();

        guiContext.setGuest(testGuest);

        assertTrue(testGuest.getId() >= 0, "Guest ID should be 0 or a positive number.");
        assertEquals(testGuest.getId(), guiContext.getId(),
                "getId() should return the correct guest ID.");
        guiContext.logout();

        assertEquals(-1, guiContext.getId(),
                "getId() should return -1 when no user or guest data is found.");
    }
}
