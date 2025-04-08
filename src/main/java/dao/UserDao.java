package dao;

import datasource.MariaDbJpaConnection;
import exception.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.User;

public class UserDao implements IDao<User> {
    private EntityManager em = MariaDbJpaConnection.getInstance();

    public void persist(User object) throws DaoException {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error saving user", e);
        }
    }

    public User find(int id) {
        return em.find(User.class, id);
    }

    public boolean existsByEmail(String email) {
        TypedQuery<User> query =
                em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return !query.getResultList().isEmpty();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email AND u.password = :password",
                User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getResultStream().findFirst();
    }

    // Find all users except admin
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u WHERE u.role <> 'admin'", User.class)
                .getResultList();
    }

    public void update(User object) throws DaoException {
        try {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error updating user", e);
        }
    }


    public void delete(User object) throws DaoException {
        try {
            em.getTransaction().begin();
            // delete all matches where the user is a participant
            em.createQuery(
                            "DELETE FROM Match m WHERE m.participant1 = :user OR m.participant2 = :user")
                    .setParameter("user", object)
                    .executeUpdate();
            em.remove(em.contains(object) ? object : em.merge(object));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DaoException("Error deleting user", e);
        }
    }

    public void deleteAll() throws DaoException {
        List<User> usersToDelete = findAll();
        for (User user : usersToDelete) {
            delete(user);
        }
        System.out.println("Deleted all users");
    }

    // For setting up test db
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
