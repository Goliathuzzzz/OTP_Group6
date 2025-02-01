package dao;

import datasource.MariaDbJpaConnection;
import jakarta.persistence.EntityManager;
import model.Guest;

import java.util.List;

public class GuestDao implements IDao<Guest> {

    public void persist(Guest object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }


    public Guest find(int id) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.find(Guest.class, id);
    }


    public List<Guest> findAll() {
        EntityManager em = MariaDbJpaConnection.getInstance();
        return em.createQuery("SELECT u FROM Guest u", Guest.class).getResultList();
    }


    public void update(Guest object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }


    public void delete(Guest object) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
    }
}
