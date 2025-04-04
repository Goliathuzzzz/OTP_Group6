package util;

import controller.GuestController;
import controller.MatchController;
import controller.UserController;
import model.Guest;
import model.User;
import model.categories.*;

import java.util.Date;


public class Seeder {
    public static void main(String[] args) {
        UserController userController = new UserController();
        GuestController guestController = new GuestController();
        MatchController matchController = new MatchController();
        userController.deleteAll();
        guestController.deleteAll();
        matchController.deleteAll();

        User user1 = new User("alice", "password1", "alice@example.com", "dummy", "1234567890", new Date(), "en");
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


        User user2 = new User("bob", "password2", "bob@example.com", "dummy", "0987654321", new Date(), "fi");
        user2.addAnimalInterest(Animal.HIIRI);
        user2.addFoodInterest(Food.KAIKKI_MENEE);
        user2.addHobbiesInterest(Hobby.INVESTOINTI);
        user2.addHobbiesInterest(Hobby.VIDEOPELIT);
        user2.addScienceInterest(Science.MATEMATIIKKA);
        user2.addScienceInterest(Science.OHJELMOINTI);
        user2.addSportsInterest(Sports.KAMPPAILULAJIT);


        User user3 = new User("charlie", "password3", "charlie@example.com", "dummy", "1122334455", new Date(), "ja");
        user3.addAnimalInterest(Animal.KOIRA);
        user3.addFoodInterest(Food.VEGAANI);
        user3.addHobbiesInterest(Hobby.TÄHTIENTARKKAILU);
        user3.addHobbiesInterest(Hobby.MEDITOINTI);
        user3.addScienceInterest(Science.TÄHTITIEDE);
        user3.addScienceInterest(Science.FYSIIKKA);
        user3.addSportsInterest(Sports.PYÖRÄILY);
        user3.addSportsInterest(Sports.LENKKEILY);

        User user4 = new User("agatha", "password4", "agatha@example.com", "dummy", "23232323211", new Date(), "zh");
        user4.addAnimalInterest(Animal.KISSA);
        user4.addFoodInterest(Food.KAIKKI_MENEE);
        user4.addHobbiesInterest(Hobby.AKTIVISMI);
        user4.addHobbiesInterest(Hobby.MEDITOINTI);
        user4.addScienceInterest(Science.KEMIA);
        user4.addSportsInterest(Sports.TENNIS);

        User admin = new User("admin", "admin", "admin@admin.com", "admin", "1234567890", new Date(), "en");

        Guest guest1 = new Guest("1231321312312", new Date());
        guestController.registerGuest(guest1);
        userController.registerUser(user1);
        userController.registerUser(user2);
        userController.registerUser(user3);
        userController.registerUser(user4);
        userController.registerUser(admin);

        matchController.matchParticipants(user1, user2, 70);
        matchController.matchParticipants(user1, user3, 50);
        matchController.matchParticipants(user1, user4, 90);
        matchController.matchParticipants(user2, user3, 20);
    }
}
