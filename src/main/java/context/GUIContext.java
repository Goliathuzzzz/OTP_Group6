package context;

import model.Guest;
import model.Matcher;
import model.Session;
import model.User;

public class GUIContext {
    private static GUIContext instance;
    private User user;
    private Guest guest;
    private Session session;
    private Matcher matcher;

    private GUIContext() {}

    public static GUIContext getInstance() {
        if (instance == null) {
            instance = new GUIContext();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
}
