package service;

import dao.GuestDao;
import exception.DaoException;
import jakarta.persistence.EntityManager;
import model.Guest;

import java.util.List;

public class GuestService {

    private final GuestDao guestDao;

    public GuestService() {
        this.guestDao = new GuestDao();
    }

    public void registerGuest(Guest guest) {
        try {
            guestDao.persist(guest);
        } catch (DaoException e) {
            System.out.println("Error registering guest: " + e.getMessage());
        }
    }

    public Guest findGuestById(int id) {
        return guestDao.find(id);
    }

    public List<Guest> getAllGuests() {
        return guestDao.findAll();
    }

    public void updateGuest(Guest guest) {
        guestDao.update(guest);
    }

    public void deleteGuest(Guest guest) {
        try {
            guestDao.delete(guest);
        } catch (DaoException e) {
            System.out.println("Error deleting guest: " + e.getMessage());
        }
    }

    public void deleteAllGuests() {
        try {
            guestDao.deleteAll();
        } catch (DaoException e) {
            System.out.println("Error deleting all guests: " + e.getMessage());
        }
    }

    // For tests
    public void setEm(EntityManager em) {
        guestDao.setEm(em);
    }
}
