package context;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import model.Guest;
import model.Matcher;
import model.Session;
import model.User;

public class GUIContext {
    private static GUIContext instance;
    private User user;
    private boolean isUser = false;
    private Guest guest;
    private Session session;
    private Matcher matcher;
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
    }

    public User getUser() {
        return user;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUserName(String name) {
        user.setUserName(name);
        setUserNameProperty(name);
    }

    public String getUserName() {
        return user.getUserName();
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
