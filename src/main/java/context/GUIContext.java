package context;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import model.*;

import java.util.List;
import java.util.Optional;

public class GUIContext {
    private static GUIContext instance;
    private User user;
    private boolean isUser = false;
    private Guest guest;
    private boolean isGuest = false;
    private boolean isAdmin = false;
    private Session session;
    private Matcher matcher;
    private List<Match> matches;
    private String language;

    @FXML
    private final StringProperty nameProperty, emailProperty, phoneProperty;

    private GUIContext() {
        this.nameProperty = new SimpleStringProperty();
        this.emailProperty = new SimpleStringProperty();
        this.phoneProperty = new SimpleStringProperty();
    }

    public static GUIContext getInstance() {
        if (instance == null) {
            instance = new GUIContext();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
        isUser = true;
        if (user.getRole().equals("admin")) {
            isAdmin = true;
        }
    }

    public User getUser() {
        return user;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUserName(String name) {
        Optional<LocalizedUser> localizedUser = user.getLocalization(language);
        if (localizedUser.isPresent()) {
            localizedUser.get().setUserName(name);
        } else {
            user.addLocalization(language, name);
        }
        setUserNameProperty(name);
    }

    public String getUserName() {
        if (user.getLocalization(language).isPresent()) {
            return user.getLocalization(language).get().getUserName();
        }
        return user.getAnyUserName();
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public String getUserPhoneNumber() {
        return user.getPhoneNumber();
    }

    public void setUserEmail(String email) {
        user.setEmail(email);
        setEmailProperty(email);
    }

    public void setUserPhoneNumber(String pn) {
        user.setPhoneNumber(pn);
        setPhoneProperty(pn);
    }

    public StringProperty getUserNameProperty() {
        return nameProperty;
    }

    public void setUserNameProperty(String name) {
        nameProperty.set(name);
    }

    public StringProperty getEmailProperty() {
        return emailProperty;
    }

    public void setEmailProperty(String email) {
        emailProperty.set(email);
    }

    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    public void setPhoneProperty(String phoneNumber) {
        phoneProperty.set(phoneNumber);
    }

    public void setSession(Session session) {
        this.session = session;
        System.out.println("DEBUG: session set in GUIContext");
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
        isGuest = true;
    }

    public Guest getGuest() {
        return guest;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getGuestPhoneNumber() {
        return guest.getPhoneNumber();
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

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void setMatch(Match match) {
        matches.add(match);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getId() {
        if (isUser) {
            return user.getId();
        } else if (isGuest) {
            return guest.getId();
        } else {
            System.err.println("ERROR: No user or guest data found");
            return -1;
        }
    }

    public void logout() {
        user = null;
        guest = null;
        session = null;
        matcher = null;
        matches = null;

        isUser = false;
        isGuest = false;
        isAdmin = false;

        nameProperty.set("");
        emailProperty.set("");
        phoneProperty.set("");
    }
}
