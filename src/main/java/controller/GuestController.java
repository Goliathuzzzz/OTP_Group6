package controller;

import jakarta.persistence.EntityManager;
import java.util.List;
import model.Guest;
import service.GuestService;

// GUI KUTSUU CONTROLLERIA!
public class GuestController {

    private final GuestService guestService;

    public GuestController() {
        this.guestService = new GuestService();
    }

    public void registerGuest(Guest guest) {
        guestService.registerGuest(guest);
        System.out.println("Guest registered successfully");
    }

    public Guest displayGuest(int id) {
        Guest guest = guestService.findGuestById(id);
        if (guest != null) {
            System.out.println(
                    "Guest" + guest.getId() + "found! PhoneNumber: " + guest.getPhoneNumber());
        } else {
            System.out.println("Guest not found");
        }
        return guest;
    }

    public List<Guest> displayAllGuests() {
        List<Guest> guests = guestService.getAllGuests();
        for (Guest guest : guests) {
            System.out.println(guest.getId() + " - " + guest.getPhoneNumber());
        }
        return guests;
    }

    public void updateGuest(Guest guest) {
        guestService.updateGuest(guest);
        System.out.println("Guest updated successfully!");
    }

    public void deleteGuest(Guest guest) {
        guestService.deleteGuest(guest);
        System.out.println("Guest deleted successfully!");
    }

    public void deleteAll() {
        guestService.deleteAllGuests();
        System.out.println("All guests deleted successfully");
    }

    // For tests
    public void setEm(EntityManager em) {
        guestService.setEm(em);
    }
}
