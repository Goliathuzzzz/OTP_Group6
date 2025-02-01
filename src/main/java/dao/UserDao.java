package dao;

import datasource.MariaDbJpaConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements IDao<User> {

    @Override
    public void persist(User object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public User find(int id) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.find(User.class, id);
    }

    public Optional<User> findByUsername(String username) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<User> findAll() {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void update(User object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public void delete(User object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
