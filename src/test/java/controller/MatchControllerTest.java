package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Match;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {

    private static MatchController matchController;
    private static User testUser;
    private static User testUser2;
    private static User testUser3;

    @BeforeAll
    static void init() {
        EntityManager em = Persistence.createEntityManagerFactory("test-persistence-unit-tt").createEntityManager();
        matchController = new MatchController();
        UserController userController = new UserController();
        matchController.setEm(em);
        userController.setEm(em);
        testUser = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        testUser2 = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser);
        userController.registerUser(testUser2);
        testUser3 = new User("Test3", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser3);
    }
    @BeforeEach
    void setUp() {
        matchController.deleteAllMatches();
    }

    @Test
    void matchParticipants() {
        matchController.matchParticipants(testUser, testUser2, 0.75);
        assertEquals(1, matchController.displayAllByParticipant(testUser).size());
    }

    @Test
    void displayAllMatches() {
        matchController.matchParticipants(testUser, testUser3, 0.6);
        matchController.matchParticipants(testUser2, testUser3, 0.8);
        assertEquals(2, matchController.displayAllMatches().size());
    }

    @Test
    void displayAllByParticipant() {
        matchController.matchParticipants(testUser, testUser3, 0.6);
        matchController.matchParticipants(testUser2, testUser3, 0.8);
        assertEquals(2, matchController.displayAllByParticipant(testUser3).size());
        assertEquals(1, matchController.displayAllByParticipant(testUser).size());
    }

    @Test
    void updateMatch() {
        matchController.matchParticipants(testUser, testUser3, 0.6);
        Match match = matchController.displayAllByParticipant(testUser).get(0);
        match.setCompatibility(0.7);
        match.setParticipant2(testUser2);
        matchController.updateMatch(match);
        assertEquals(0.7, matchController.displayAllByParticipant(testUser).get(0).getCompatibility());
        assertEquals(testUser2,  matchController.displayAllByParticipant(testUser).get(0).getParticipant2());
    }

    @Test
    void deleteMatch() {
        matchController.matchParticipants(testUser, testUser3, 0.6);
        matchController.matchParticipants(testUser, testUser2, 0.6);
        assertEquals(2, matchController.displayAllByParticipant(testUser).size());
        matchController.deleteMatch(matchController.displayAllByParticipant(testUser).get(0));
        assertEquals(1, matchController.displayAllByParticipant(testUser).size());
        assertNotNull(matchController.displayAllByParticipant(testUser).get(0));
    }

    @Test
    void deleteAllMatches() {
        matchController.matchParticipants(testUser, testUser3, 0.6);
        matchController.matchParticipants(testUser, testUser2, 0.6);
        matchController.deleteAllMatches();
        assertEquals(0, matchController.displayAllMatches().size());
    }
}