package util;

import controller.GuestController;
import controller.MatchController;
import controller.UserController;
import model.Guest;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.Mockito.*;

class SeederTest {

    private UserController userController;
    private GuestController guestController;
    private MatchController matchController;

    @BeforeEach
    void setUp() {
        userController = mock(UserController.class);
        guestController = mock(GuestController.class);
        matchController = mock(MatchController.class);
    }

    @Test
    void testSeederOperations() {
        doNothing().when(userController).deleteAll();
        doNothing().when(guestController).deleteAll();
        doNothing().when(matchController).deleteAll();

        userController.deleteAll();
        guestController.deleteAll();
        matchController.deleteAll();

        verify(userController).deleteAll();
        verify(guestController).deleteAll();
        verify(matchController).deleteAll();

        User user1 = new User("alice", "password1", "alice@example.com", "dummy", "1234567890", new Date());
        User user2 = new User("bob", "password2", "bob@example.com", "dummy", "0987654321", new Date());
        User user3 = new User("charlie", "password3", "charlie@example.com", "dummy", "1122334455", new Date());
        User user4 = new User("agatha", "password4", "agatha@example.com", "dummy", "23232323211", new Date());
        User admin = new User("admin", "admin", "admin@admin.com", "admin", "1234567890", new Date());

        Guest guest1 = new Guest("1231321312312", new Date());

        doNothing().when(userController).registerUser(any(User.class));
        doNothing().when(guestController).registerGuest(any(Guest.class));

        guestController.registerGuest(guest1);
        userController.registerUser(user1);
        userController.registerUser(user2);
        userController.registerUser(user3);
        userController.registerUser(user4);
        userController.registerUser(admin);

        verify(guestController).registerGuest(guest1);
        verify(userController).registerUser(user1);
        verify(userController).registerUser(user2);
        verify(userController).registerUser(user3);
        verify(userController).registerUser(user4);
        verify(userController).registerUser(admin);

        doNothing().when(matchController).matchParticipants(any(User.class), any(User.class), anyInt());

        matchController.matchParticipants(user1, user2, 70);
        matchController.matchParticipants(user1, user3, 50);
        matchController.matchParticipants(user1, user4, 90);
        matchController.matchParticipants(user2, user3, 20);

        verify(matchController).matchParticipants(user1, user2, 70);
        verify(matchController).matchParticipants(user1, user3, 50);
        verify(matchController).matchParticipants(user1, user4, 90);
        verify(matchController).matchParticipants(user2, user3, 20);
    }
}