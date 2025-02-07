package util;

import controller.GuestController;
import controller.UserController;
import model.User;
import model.categories.*;
import service.GuestService;
import service.UserService;

import java.util.Date;


public class Seeder {
    public static void main(String[] args) {
        UserController userController = new UserController();
        GuestController guestController = new GuestController();
        userController.deleteAll();
        guestController.deleteAll();

        User user1 = new User("Alice", "password1", "alice@example.com", "dummy", "1234567890");
        user1.setJoinDate(new Date());
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


        User user2 = new User("Bob", "password2", "bob@example.com", "dummy", "0987654321");
        user2.setJoinDate(new Date());
        user2.addAnimalInterest(Animal.MOUSE);
        user2.addFoodInterest(Food.ANYTHING_GOES);
        user2.addHobbiesInterest(Hobby.INVESTING);
        user2.addHobbiesInterest(Hobby.VIDEO_GAMES);
        user2.addScienceInterest(Science.MATHEMATICS);
        user2.addScienceInterest(Science.PROGRAMMING);
        user2.addSportsInterest(Sports.MARTIAL_ARTS);


        User user3 = new User("Charlie", "password3", "charlie@example.com", "dummy", "1122334455");
        user3.setJoinDate(new Date());
        user3.addAnimalInterest(Animal.DOG);
        user3.addFoodInterest(Food.VEGAN);
        user3.addHobbiesInterest(Hobby.STARGAZING);
        user3.addHobbiesInterest(Hobby.MEDITATION);
        user3.addScienceInterest(Science.ASTRONOMY);
        user3.addScienceInterest(Science.PHYSICS);
        user3.addSportsInterest(Sports.CYCLING);
        user3.addSportsInterest(Sports.RUNNING);

        User user4 = new User("Agatha", "password4", "agatha@example.com", "dummy", "23232323211");
        user4.setJoinDate(new Date());
        user4.addAnimalInterest(Animal.CAT);
        user4.addFoodInterest(Food.ANYTHING_GOES);
        user4.addHobbiesInterest(Hobby.ACTING);
        user4.addHobbiesInterest(Hobby.MEDITATION);
        user4.addScienceInterest(Science.CHEMISTRY);
        user4.addSportsInterest(Sports.TENNIS);

        userController.registerUser(user1);
        userController.registerUser(user2);
        userController.registerUser(user3);
        userController.registerUser(user4);
    }

}
