package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.categories.Animal;
import model.categories.Category;
import model.categories.Food;
import model.categories.Hobby;
import model.categories.Science;
import model.categories.Sports;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {

    private static Session session;
    private static List<Category> interests;

    @BeforeAll
    static void init() {
        Participant participant = new Guest("123124124124", new Date());
        session = new Session(participant);
        interests = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        session.getParticipantInterests().clear();
        interests.clear();
    }

    @Test
    void addParticipantInterest() {
        session.addParticipantInterest(Animal.KISSA);
        interests.add(Animal.KISSA);
        session.addParticipantInterest(Hobby.INVESTOINTI);
        interests.add(Hobby.INVESTOINTI);
        session.addParticipantInterest(Science.TÄHTITIEDE);
        interests.add(Science.TÄHTITIEDE);
        session.addParticipantInterest(Sports.UIMINEN);
        interests.add(Sports.UIMINEN);
        session.addParticipantInterest(Food.KAIKKI_MENEE);
        interests.add(Food.KAIKKI_MENEE);
        assertEquals(interests, session.getParticipantInterests());
    }

    @Test
    void removeParticipantInterest() {
        session.addParticipantInterest(Animal.KISSA);
        session.addParticipantInterest(Animal.HEVONEN);
        session.addParticipantInterest(Hobby.TV_SARJAT);
        assertEquals(3, session.getParticipantInterests().size());
        session.removeParticipantInterest(Animal.KISSA);
        assertEquals(2, session.getParticipantInterests().size());
        assertFalse(session.getParticipantInterests().contains(Animal.KISSA));
        session.removeParticipantInterest(Animal.KOIRA);
        assertEquals(2, session.getParticipantInterests().size());
    }

    @Test
    void nullParticipantGivesException() {
        assertThrows(IllegalArgumentException.class, () -> new Session(null));
    }

}
