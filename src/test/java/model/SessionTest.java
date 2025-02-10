package model;

import model.categories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        session.addParticipantInterest(Animal.DOG);
        interests.add(Animal.DOG);
        session.addParticipantInterest(Hobby.INVESTING);
        interests.add(Hobby.INVESTING);
        session.addParticipantInterest(Science.ASTRONOMY);
        interests.add(Science.ASTRONOMY);
        session.addParticipantInterest(Sports.SWIMMING);
        interests.add(Sports.SWIMMING);
        session.addParticipantInterest(Food.ANYTHING_GOES);
        interests.add(Food.ANYTHING_GOES);
        assertEquals(interests, session.getParticipantInterests());
    }

    @Test
    void removeParticipantInterest() {
        session.addParticipantInterest(Animal.CAT);
        session.addParticipantInterest(Animal.HORSE);
        session.addParticipantInterest(Hobby.SHOWS);
        assertEquals(3, session.getParticipantInterests().size());
        session.removeParticipantInterest(Animal.CAT);
        assertEquals(2, session.getParticipantInterests().size());
        assertFalse(session.getParticipantInterests().contains(Animal.CAT));
        session.removeParticipantInterest(Animal.DOG);
        assertEquals(2, session.getParticipantInterests().size());
    }

}