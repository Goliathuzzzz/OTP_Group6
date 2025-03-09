package controller;

import jakarta.persistence.Persistence;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private static UserController userController;

    @BeforeAll
    static void init() {
        userController = new UserController();
        userController.setEm(Persistence.createEntityManagerFactory("test-persistence-unit-tt").createEntityManager());
    }

    @BeforeEach
    void setUp() {
        userController.deleteAll();
    }

    @Test
    void testRegisterUser() {
        User testUser = new User("Test", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser);
        assertEquals(testUser, userController.displayUser(testUser.getId()), "Users should match");
    }

    @Test
    void testDisplayAllUsers() {
        List<User> users = new ArrayList<>();
        User testUser = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser);
        users.add(testUser);
        User testUser2 = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser2);
        users.add(testUser2);
        assertEquals(users, userController.displayAllUsers(), "Lists should match");
    }

    @Test
    void testUpdateUser() {
        User testUser = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser);
        testUser.setEmail("changed@email.com");
        testUser.setRole("Hello-world");
        userController.updateUser(testUser);
        assertEquals(testUser, userController.displayUser(testUser.getId()));
    }

    @Test
    void testDeleteUser() {
        User testUser = new User("Test", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser);
        assertEquals(testUser, userController.displayUser(testUser.getId()), "Should be 1 user");
        userController.deleteUser(testUser);
        assertNull(userController.displayUser(testUser.getId()));
    }

    @Test
    void testDeleteAll() {
        User testUser = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser);
        User testUser2 = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser2);
        assertEquals(2, userController.displayAllUsers().size(), "Should have 2 users");
        userController.deleteAll();
        assertEquals(0, userController.displayAllUsers().size());
    }

    @Test
    void testLogin() {
        User testUser = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(testUser);
        User loggedInUser = userController.login("test@email.com", "testpassword");
        assertEquals(testUser, loggedInUser);
        User fakeUser = userController.login("fake@email.com", "fakepassword");
        assertNull(fakeUser);
    }

    @Test
    void testExistsByEmail() {
        assertFalse(userController.existsByEmail("some@email.com"));
        User newUser = new User("Test2", "testpassword", "test@email.com", "test-subject", "123456789", new Date());
        userController.registerUser(newUser);
        assertTrue(userController.existsByEmail("test@email.com"));
    }
}