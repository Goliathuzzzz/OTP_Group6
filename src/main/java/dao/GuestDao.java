package dao;

import datasource.MariaDbJpaConnection;
import jakarta.persistence.EntityManager;
import model.Guest;

import java.util.List;

public class GuestDao implements IDao<Guest> {
    @Override
    public void persist(Guest object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    @Override
    public Guest find(int id) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.find(Guest.class, id);
    }

    @Override
    public List<Guest> findAll() {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.createQuery("SELECT u FROM Guest u", Guest.class).getResultList();
    }

    @Override
    public void update(Guest object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Guest object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
    }
}
