package dao;

import datasource.MariaDbJpaConnection;
import exception.DaoException;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.Match;
import model.Participant;

public class MatchDao implements IDao<Match> {
    private EntityManager em = MariaDbJpaConnection.getInstance();

    public void persist(Match object) throws DaoException {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error saving match", e);
        }
    }


    public Match find(int id) {
        return em.find(Match.class, id);
    }

    public List<Match> findAll() {
        return em.createQuery("SELECT u FROM Match u", Match.class).getResultList();
    }

    public List<Match> findByParticipant(Participant participant) {
        return em.createQuery(
                "SELECT m FROM Match m WHERE m.participant1 = :participant OR m.participant2 = :participant",
                        Match.class)
                .setParameter("participant", participant)
                .getResultList();
    }

    public void update(Match object) throws DaoException {
        try {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error updating match", e);
        }
    }


    public void delete(Match object) throws DaoException {
        try {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error deleting match", e);
        }
    }

    public void deleteAll() throws DaoException {
        List<Match> matchesToDelete = findAll();
        for (Match m : matchesToDelete) {
            delete(m);
        }
        System.out.println("Deleted all matches");
    }

    // For setting up test db
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
