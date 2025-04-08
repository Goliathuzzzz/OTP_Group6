package service;

import dao.UserDao;
import exception.DaoException;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import model.User;

public class UserService { //"good practice", handles the business logic

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public void registerUser(User user) {
        try {
            userDao.persist(user);
        } catch (DaoException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    public User findUserById(int id) {
        return userDao.find(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(User user) {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    public void deleteUser(User user) {
        try {
            userDao.delete(user);
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    public void deleteAllUsers() {
        try {
            userDao.deleteAll();
        } catch (Exception e) {
            System.out.println("Error deleting all users: " + e.getMessage());
        }
    }

    // For setting up test db
    public void setUserDaoEm(EntityManager em) {
        userDao.setEm(em);
    }

    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }
}
