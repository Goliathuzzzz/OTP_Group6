package model;

import controller.UserController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.categories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MatcherTest {

    private static Session session;
    private static Matcher matcher;

    @BeforeAll
    static void init() {
        session = new Session(new Guest("21313233", new Date()));
        matcher = new Matcher(session);
        EntityManager em = Persistence.createEntityManagerFactory("test-persistence-unit-tt").createEntityManager();
        matcher.getUserController().setEm(em);
        seedTestDB(matcher.getUserController());
    }

    @BeforeEach
    void setUp() {
        session.getParticipantInterests().clear();
        matcher.getTopMatches().clear();
    }

    @Test
    void matchParticipant() {
        session.addParticipantInterest(Animal.HEVONEN);
        session.addParticipantInterest(Animal.KOIRA);
        session.addParticipantInterest(Animal.KISSA);
        session.addParticipantInterest(Hobby.AKTIVISMI);
        session.addParticipantInterest(Hobby.TV_SARJAT);
        session.addParticipantInterest(Sports.LENKKEILY);
        session.addParticipantInterest(Science.TÄHTITIEDE);
        session.addParticipantInterest(Science.BIOLOGIA);
        matcher.matchParticipant();
        double compatibility = matcher.getTopMatches().entrySet().iterator().next().getValue();
        assertEquals(66.67, compatibility, 0.01, "Should have 66.67% compatibility");
        User expected = matcher.getUserController().displayUser(1);
        User actual = matcher.getTopMatches().entrySet().iterator().next().getKey();
        assertEquals(expected, actual, "Users should match");
        session.getParticipantInterests().clear();
        matcher.getTopMatches().clear();
        session.addParticipantInterest(Animal.KOIRA);
        session.addParticipantInterest(Food.VEGAANI);
        session.addParticipantInterest(Hobby.MEDITOINTI);
        session.addParticipantInterest(Hobby.TÄHTIENTARKKAILU);
        session.addParticipantInterest(Science.FYSIIKKA);
        session.addParticipantInterest(Science.TÄHTITIEDE);
        matcher.matchParticipant();
        compatibility = matcher.getTopMatches().entrySet().iterator().next().getValue();
        expected = matcher.getUserController().displayUser(3);
        actual = matcher.getTopMatches().entrySet().iterator().next().getKey();
        assertEquals(75, compatibility, 0.01, "Should have 75% compatibility");
        assertEquals(expected, actual, "Users should match");

    }

    @Test
    void matchParticipantWithoutInterests() {
        matcher.matchParticipant();
        assertEquals(0, matcher.getTopMatches().size(), "There should be no matches");
    }

    @Test
    void matchParticipantMultipleMatches() {
        session.addParticipantInterest(Animal.KISSA);
        session.addParticipantInterest(Science.BIOLOGIA);
        matcher.matchParticipant();
        assertEquals(2, matcher.getTopMatches().size(), "There should be 2 matches");
        double compatibility = matcher.getTopMatches().entrySet().iterator().next().getValue();
        assertEquals(16.67, compatibility, 0.01, "Compatibility should be 16.67%");
    }


    private static void seedTestDB(UserController uS) {
        User user1 = new User("Alice", "password1", "alice@example.com", "dummy", "1234567890",new Date());
        user1.addAnimalInterest(Animal.HEVONEN);
        user1.addAnimalInterest(Animal.KOIRA);
        user1.addAnimalInterest(Animal.KISSA);
        user1.addFoodInterest(Food.VEGETARISTI);
        user1.addFoodInterest(Food.VEGAANI);
        user1.addHobbiesInterest(Hobby.AKTIVISMI);
        user1.addHobbiesInterest(Hobby.TV_SARJAT);
        user1.addHobbiesInterest(Hobby.JULKINEN_PUHUMINEN);
        user1.addSportsInterest(Sports.LENKKEILY);
        user1.addSportsInterest(Sports.UIMINEN);
        user1.addScienceInterest(Science.TÄHTITIEDE);
        user1.addScienceInterest(Science.BIOLOGIA);


        User user2 = new User("Bob", "password2", "bob@example.com", "dummy", "0987654321",new Date());
        user2.addAnimalInterest(Animal.HIIRI);
        user2.addFoodInterest(Food.KAIKKI_MENEE);
        user2.addHobbiesInterest(Hobby.INVESTOINTI);
        user2.addHobbiesInterest(Hobby.VIDEOPELIT);
        user2.addScienceInterest(Science.MATEMATIIKKA);
        user2.addScienceInterest(Science.OHJELMOINTI);
        user2.addSportsInterest(Sports.KAMPPAILULAJIT);


        User user3 = new User("Charlie", "password3", "charlie@example.com", "dummy", "1122334455", new Date());
        user3.addAnimalInterest(Animal.KOIRA);
        user3.addFoodInterest(Food.VEGAANI);
        user3.addHobbiesInterest(Hobby.TÄHTIENTARKKAILU);
        user3.addHobbiesInterest(Hobby.MEDITOINTI);
        user3.addScienceInterest(Science.TÄHTITIEDE);
        user3.addScienceInterest(Science.FYSIIKKA);
        user3.addSportsInterest(Sports.PYÖRÄILY);
        user3.addSportsInterest(Sports.LENKKEILY);

        User user4 = new User("Agatha", "password4", "agatha@example.com", "dummy", "23232323211", new Date());
        user4.addAnimalInterest(Animal.KISSA);
        user4.addFoodInterest(Food.KAIKKI_MENEE);
        user4.addHobbiesInterest(Hobby.NÄYTTELEMINEN);
        user4.addHobbiesInterest(Hobby.MEDITOINTI);
        user4.addScienceInterest(Science.KEMIA);
        user4.addSportsInterest(Sports.TENNIS);
        uS.registerUser(user1);
        uS.registerUser(user2);
        uS.registerUser(user3);
        uS.registerUser(user4);

    }
}