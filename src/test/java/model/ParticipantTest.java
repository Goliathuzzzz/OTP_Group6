package model;

import model.categories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    private Participant participant;
    private List<Category> expectedInterests;

    @BeforeEach
    void setUp() {
        participant = new Guest("123456789", new Date());
        expectedInterests = new ArrayList<>();
    }

    @Test
    void testGetId() {
        assertEquals(0, participant.getId(), "Default ID should be 0 before persisting.");
    }

    @Test
    void testGetPhoneNumber() {
        assertEquals("123456789", participant.getPhoneNumber(), "Phone number should match the provided value.");
    }

    @Test
    void testSetPhoneNumber() {
        participant.setPhoneNumber("987654321");
        assertEquals("987654321", participant.getPhoneNumber(), "Phone number should be updated.");
    }

    @Test
    void testGetJoinDate() {
        assertNotNull(participant.getJoinDate(), "Join date should not be null.");
    }

    @Test
    void testJoinDate() {
        Date joinDate = new Date();
        participant.setJoinDate(joinDate);
        assertEquals(joinDate, participant.getJoinDate(), "Join date should be updated.");
    }

    @Test
    void testGetInterests() {
        participant.addAnimalInterest(Animal.KISSA);
        participant.addFoodInterest(Food.KAIKKI_MENEE);
        participant.addHobbiesInterest(Hobby.TV_SARJAT);
        participant.addScienceInterest(Science.TÄHTITIEDE);
        participant.addSportsInterest(Sports.UIMINEN);

        expectedInterests.add(Animal.KISSA);
        expectedInterests.add(Food.KAIKKI_MENEE);
        expectedInterests.add(Hobby.TV_SARJAT);
        expectedInterests.add(Science.TÄHTITIEDE);
        expectedInterests.add(Sports.UIMINEN);

        assertEquals(expectedInterests, participant.getInterests(), "All added interests should be present.");
    }

    @Test
    void testClearInterests() {
        participant.addAnimalInterest(Animal.KISSA);
        participant.addFoodInterest(Food.KAIKKI_MENEE);
        participant.addScienceInterest(Science.TÄHTITIEDE);
        participant.addHobbiesInterest(Hobby.TV_SARJAT);
        participant.addSportsInterest(Sports.UIMINEN);

        participant.clearInterests();

        assertTrue(participant.getInterests().isEmpty(), "All interests should be cleared.");
    }

    @Test
    void testAddAnimalInterest() {
        participant.addAnimalInterest(Animal.KISSA);
        assertTrue(participant.getAnimalInterests().contains(Animal.KISSA), "Animal interest should be added.");
    }

    @Test
    void testAddFoodInterest() {
        participant.addFoodInterest(Food.KAIKKI_MENEE);
        assertTrue(participant.getFoodInterests().contains(Food.KAIKKI_MENEE), "Food interest should be added.");
    }

    @Test
    void testAddScienceInterest() {
        participant.addScienceInterest(Science.TÄHTITIEDE);
        assertTrue(participant.getScienceInterests().contains(Science.TÄHTITIEDE), "Science interest should be added.");
    }

    @Test
    void testAddHobbyInterest() {
        participant.addHobbiesInterest(Hobby.TV_SARJAT);
        assertTrue(participant.getHobbiesInterests().contains(Hobby.TV_SARJAT), "Hobby interest should be added.");
    }

    @Test
    void testAddSportsInterest() {
        participant.addSportsInterest(Sports.UIMINEN);
        assertTrue(participant.getSportsInterests().contains(Sports.UIMINEN), "Sports interest should be added.");
    }

    @Test
    void testGetDisplayName() {
        assertEquals("vieras0", participant.getDisplayName(), "Display name should follow format 'vieras' + id.");
    }
}
