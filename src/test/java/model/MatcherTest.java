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
        session.addParticipantInterest(Animal.HORSE);
        session.addParticipantInterest(Animal.DOG);
        session.addParticipantInterest(Animal.CAT);
        session.addParticipantInterest(Hobby.ACTIVISM);
        session.addParticipantInterest(Hobby.SHOWS);
        session.addParticipantInterest(Sports.RUNNING);
        session.addParticipantInterest(Science.ASTRONOMY);
        session.addParticipantInterest(Science.BIOLOGY);
        matcher.matchParticipant();
        double compatibility = matcher.getTopMatches().entrySet().iterator().next().getValue();
        assertEquals(66.67, compatibility, 0.01, "Should have 66.67% compatibility");
        User expected = matcher.getUserController().displayUser(1);
        User actual = matcher.getTopMatches().entrySet().iterator().next().getKey();
        assertEquals(expected, actual, "Users should match");
        session.getParticipantInterests().clear();
        matcher.getTopMatches().clear();
        session.addParticipantInterest(Animal.DOG);
        session.addParticipantInterest(Food.VEGAN);
        session.addParticipantInterest(Hobby.MEDITATION);
        session.addParticipantInterest(Hobby.STARGAZING);
        session.addParticipantInterest(Science.PHYSICS);
        session.addParticipantInterest(Science.ASTRONOMY);
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
        session.addParticipantInterest(Animal.CAT);
        session.addParticipantInterest(Science.BIOLOGY);
        matcher.matchParticipant();
        assertEquals(2, matcher.getTopMatches().size(), "There should be 2 matches");
        double compatibility = matcher.getTopMatches().entrySet().iterator().next().getValue();
        assertEquals(16.67, compatibility, 0.01, "Compatibility should be 16.67%");
    }


    private static void seedTestDB(UserController uS) {
        User user1 = new User("Alice", "password1", "alice@example.com", "dummy", "1234567890",new Date());
        user1.addAnimalInterest(Animal.HORSE);
        user1.addAnimalInterest(Animal.DOG);
        user1.addAnimalInterest(Animal.CAT);
        user1.addFoodInterest(Food.VEGETARIAN);
        user1.addFoodInterest(Food.VEGAN);
        user1.addHobbiesInterest(Hobby.ACTIVISM);
        user1.addHobbiesInterest(Hobby.SHOWS);
        user1.addHobbiesInterest(Hobby.PUBLIC_SPEAKING);
        user1.addSportsInterest(Sports.RUNNING);
        user1.addSportsInterest(Sports.SWIMMING);
        user1.addScienceInterest(Science.ASTRONOMY);
        user1.addScienceInterest(Science.BIOLOGY);


        User user2 = new User("Bob", "password2", "bob@example.com", "dummy", "0987654321",new Date());
        user2.addAnimalInterest(Animal.MOUSE);
        user2.addFoodInterest(Food.ANYTHING_GOES);
        user2.addHobbiesInterest(Hobby.INVESTING);
        user2.addHobbiesInterest(Hobby.VIDEO_GAMES);
        user2.addScienceInterest(Science.MATHEMATICS);
        user2.addScienceInterest(Science.PROGRAMMING);
        user2.addSportsInterest(Sports.MARTIAL_ARTS);


        User user3 = new User("Charlie", "password3", "charlie@example.com", "dummy", "1122334455", new Date());
        user3.addAnimalInterest(Animal.DOG);
        user3.addFoodInterest(Food.VEGAN);
        user3.addHobbiesInterest(Hobby.STARGAZING);
        user3.addHobbiesInterest(Hobby.MEDITATION);
        user3.addScienceInterest(Science.ASTRONOMY);
        user3.addScienceInterest(Science.PHYSICS);
        user3.addSportsInterest(Sports.CYCLING);
        user3.addSportsInterest(Sports.RUNNING);

        User user4 = new User("Agatha", "password4", "agatha@example.com", "dummy", "23232323211", new Date());
        user4.addAnimalInterest(Animal.CAT);
        user4.addFoodInterest(Food.ANYTHING_GOES);
        user4.addHobbiesInterest(Hobby.ACTING);
        user4.addHobbiesInterest(Hobby.MEDITATION);
        user4.addScienceInterest(Science.CHEMISTRY);
        user4.addSportsInterest(Sports.TENNIS);
        uS.registerUser(user1);
        uS.registerUser(user2);
        uS.registerUser(user3);
        uS.registerUser(user4);

    }
}