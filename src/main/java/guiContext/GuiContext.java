package guiContext;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Guest;
import model.LocalizedUser;
import model.Match;
import model.Matcher;
import model.Session;
import model.User;

public class GuiContext {
    private static GuiContext instance;
    @FXML
    private final StringProperty nameProperty, emailProperty, phoneProperty;
    private User user;
    private volatile boolean isUser;
    private Guest guest;
    private volatile boolean isGuest;
    private volatile boolean isAdmin;
    private Session session;
    private Matcher matcher;
    private List<Match> matches;
    private String language;
    private Stage stage;
    private final Logger logger = Logger.getLogger(getClass().getName());

    private GuiContext() {
        this.nameProperty = new SimpleStringProperty();
        this.emailProperty = new SimpleStringProperty();
        this.phoneProperty = new SimpleStringProperty();
    }

    public static synchronized GuiContext getInstance() {
        if (instance == null) {
            instance = new GuiContext();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(User user) {
        this.user = user;
        isUser = true;
        if ("admin".equals(user.getRole())) {
            isAdmin = true;
        }
    }

    public String getUserName() {
        Optional<LocalizedUser> localizedUser = user.getLocalization(language);
        if (localizedUser.isEmpty()) {
            return user.getAnyUserName();
        }
        return localizedUser.get().getUserName();
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

    public String getUserEmail() {
        return user.getEmail();
    }

    public void setUserEmail(String email) {
        user.setEmail(email);
        setEmailProperty(email);
    }

    public String getUserPhoneNumber() {
        return user.getPhoneNumber();
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

    public Guest getGuest() {
        return guest;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
        isGuest = true;
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

    public void setSession(Session session) {
        this.session = session;
        logger.info("DEBUG: session set in GUIContext");
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public int getId() {
        if (isUser) {
            return user.getId();
        } else if (isGuest) {
            return guest.getId();
        } else {
            logger.info("ERROR: No user or guest data found");
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
