package scheduler;

import dao.GuestDao;
import model.Guest;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GuestCleanupTask extends TimerTask {

    private final GuestDao guestDao = new GuestDao();

    @Override
    public void run() {
        Date currentDate = new Date();
        List<Guest> expiredGuests = guestDao.findExpiredGuests(currentDate);
        for (Guest guest : expiredGuests) {
            guestDao.delete(guest);
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new GuestCleanupTask(), 0, 24 * 60 * 60 * 1000); // Run once a day
    }
}