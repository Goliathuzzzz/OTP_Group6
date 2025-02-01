package controller;

import model.User;
import service.UserService;
import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void registerUser(String userName, String password, String email, String role) {
        userService.registerUser(userName, password, email, role);
        System.out.println("User registered successfully.");
    }

    public void displayUser(int id) {
        User user = userService.findUserById(id);
        if (user != null) {
            System.out.println("User found: " + user.getUserName() + " (" + user.getEmail() + ")");
        } else {
            System.out.println("User not found.");
        }
    }

    public void displayAllUsers() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user.getUserName() + " - " + user.getEmail());
        }
    }

    public void updateUser(User user) {
        userService.updateUser(user);
        System.out.println("User updated successfully.");
    }

    public void deleteUser(User user) {
        userService.deleteUser(user);
        System.out.println("User deleted successfully.");
    }
}
