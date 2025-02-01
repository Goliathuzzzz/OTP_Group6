package dao;

import datasource.MariaDbJpaConnection;
import jakarta.persistence.EntityManager;
import model.User;

import java.util.List;

public class UserDao implements IDao<User> {
    @Override
    public void persist(User object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    @Override
    public User find(int id) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void update(User object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }

    @Override
    public void delete(User object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
    }
}
