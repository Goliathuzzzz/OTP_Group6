package dao;

import datasource.MariaDbJpaConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements IDao<User> {
    private EntityManager em = MariaDbJpaConnection.getInstance();

    public void persist(User object) {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving user", e);
        }
    }


    public User find(int id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    public boolean existsByEmail(String email) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return !query.getResultList().isEmpty();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getResultStream().findFirst();
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


    public void update(User object) {
        try {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error updating user", e);
        }
    }


    public void delete(User object) {
        try {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public void deleteAll() {
        List<User> usersToDelete = findAll();
        for (User user: usersToDelete) {
            delete(user);
        }
        System.out.println("Deleted all users");
    }

    // For setting up test db
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
