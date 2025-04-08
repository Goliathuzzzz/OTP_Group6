package dao;

import datasource.MariaDbJpaConnection;
import exception.DaoException;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.Guest;

public class GuestDao implements IDao<Guest> {
    private EntityManager em = MariaDbJpaConnection.getInstance();

    public void persist(Guest object) throws DaoException {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error saving guest", e);
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


    public void delete(Guest object) throws DaoException {
        try {
            em.getTransaction().begin();
            // delete all matches where the guest is a participant
            em.createQuery(
                            "DELETE FROM Match m WHERE m.participant1 = :guest OR m.participant2 = :guest")
                    .setParameter("guest", object)
                    .executeUpdate();
            em.remove(em.contains(object) ? object : em.merge(object));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error deleting guest", e);
        }
    }

    public void deleteAll() throws DaoException {
        List<Guest> guestsToDelete = findAll();
        for (Guest guest : guestsToDelete) {
            delete(guest);
        }
        System.out.println("All guests deleted");
    }

    // For tests
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
