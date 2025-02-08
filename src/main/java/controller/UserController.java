package controller;

import jakarta.persistence.EntityManager;
import model.User;
import service.UserService;
import java.util.List;

// GUI KUTSUU CONTROLLERIA!
public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void registerUser(User user) {
        userService.registerUser(user);
        System.out.println("User registered successfully.");
    }

    public User displayUser(int id) {
        User user = userService.findUserById(id);
        if (user != null) {
            System.out.println("User found: " + user.getUserName() + " (" + user.getEmail() + ")");
        } else {
            System.out.println("User not found.");
        }
        return user;
    }

    public List<User> displayAllUsers() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user.getUserName() + " - " + user.getEmail());
        }
        return users;
    }

    public void updateUser(User user) {
        userService.updateUser(user);
        System.out.println("User updated successfully.");
    }

    public void deleteUser(User user) {
        userService.deleteUser(user);
        System.out.println("User deleted successfully.");
    }

    public void deleteAll() {
        userService.deleteAllUsers();
        System.out.println("All users deleted successfully");
    }
    // For setting up test db
    public void setEm(EntityManager em) {
        userService.setUserDaoEm(em);
    }
}
