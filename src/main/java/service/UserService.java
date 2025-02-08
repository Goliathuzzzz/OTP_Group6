package service;

import dao.UserDao;
import model.User;
import java.util.List;

public class UserService { //"good practice", handles the business logic

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public void registerUser(User user) {
        userDao.persist(user);
    }

    public User findUserById(int id) {
        return userDao.find(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public void deleteAllUsers() {userDao.deleteAll();}
}
