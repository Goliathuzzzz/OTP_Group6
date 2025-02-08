package dao;

import datasource.MariaDbJpaConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Guest;

import java.util.Date;
import java.util.List;

public class GuestDao implements IDao<Guest> {
    private EntityManager em = MariaDbJpaConnection.getInstance();

    public void persist(Guest object) {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving guest", e);
        }
    }


    public Guest find(int id) {
        return em.find(Guest.class, id);
    }


    public List<Guest> findAll() {
        return em.createQuery("SELECT u FROM Guest u", Guest.class).getResultList();
    }


    public void update(Guest object) {
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }


    public void delete(Guest object) {
        try {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting guest", e);
        }
    }

    public List<Guest> findExpiredGuests(Date currentDate) {
        TypedQuery<Guest> query = em.createQuery("SELECT g FROM Guest g WHERE g.deleteDate <= :currentDate", Guest.class);
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }

    public void deleteAll() {
        List<Guest> guestsToDelete = findAll();
        for (Guest guest: guestsToDelete) {
            delete(guest);
        }
        System.out.println("All guests deleted");
    }

    // For tests
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
