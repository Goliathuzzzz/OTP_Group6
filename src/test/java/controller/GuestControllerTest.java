package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Guest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuestControllerTest {

    private static GuestController guestController;

    @BeforeAll
    static void init() {
        guestController = new GuestController();
    }

    @BeforeEach
    void setUp() {
        guestController.deleteAll();
    }

    @Test
    void registerGuest() {
        Guest testGuest1 = new Guest("13212312412", new Date());
        guestController.registerGuest(testGuest1);
        assertEquals(testGuest1, guestController.displayGuest(testGuest1.getId()),
                "Guests should match");
    }

    @Test
    void displayAllGuests() {
        List<Guest> guests = new ArrayList<>();
        Guest testGuest1 = new Guest("13212312412", new Date());
        Guest testGuest2 = new Guest("132123122", new Date());
        guestController.registerGuest(testGuest1);
        guestController.registerGuest(testGuest2);
        guests.add(testGuest1);
        guests.add(testGuest2);
        assertEquals(2, guestController.displayAllGuests().size(), "Lists should be equal length");
        assertEquals(guests, guestController.displayAllGuests(), "Lists should be equal");
    }

    @Test
    void updateGuest() {
        Guest testGuest1 = new Guest("13212312412", new Date());
        guestController.registerGuest(testGuest1);
        assertEquals(testGuest1, guestController.displayGuest(testGuest1.getId()),
                "Guest should be registered");
        testGuest1.setPhoneNumber("666666666");
        guestController.updateGuest(testGuest1);
        assertEquals(testGuest1, guestController.displayGuest(testGuest1.getId()),
                "Guest should be updated");
    }

    @Test
    void deleteGuest() {
        Guest testGuest1 = new Guest("13212312412", new Date());
        guestController.registerGuest(testGuest1);
        guestController.deleteGuest(testGuest1);
        assertNull(guestController.displayGuest(testGuest1.getId()),
                "Should return null if guest doesn't exist");
    }

    @Test
    void deleteAll() {
        Guest testGuest1 = new Guest("13212312412", new Date());
        Guest testGuest2 = new Guest("132123122", new Date());
        guestController.registerGuest(testGuest1);
        guestController.registerGuest(testGuest2);
        assertEquals(2, guestController.displayAllGuests().size(), "There should be 2 guests");
        guestController.deleteAll();
        assertEquals(0, guestController.displayAllGuests().size(), "there should be 0 guests");
    }
}
