package service;

import dao.GuestDao;
import model.Guest;

import java.util.List;

public class GuestService {

    private final GuestDao guestDao;

    public GuestService() {
        this.guestDao = new GuestDao();
    }

    public void registerGuest(String phoneNumber) {
        Guest guest = new Guest(phoneNumber);
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
}
