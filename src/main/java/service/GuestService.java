package service;

import dao.GuestDao;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.Guest;

public class GuestService {

    private final GuestDao guestDao;

    public GuestService() {
        this.guestDao = new GuestDao();
    }

    public void registerGuest(Guest guest) {
        guestDao.persist(guest);
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
        guestDao.delete(guest);
    }

    public void deleteAllGuests() {
        guestDao.deleteAll();
    }

    // For tests
    public void setEm(EntityManager em) {
        guestDao.setEm(em);
    }
}
