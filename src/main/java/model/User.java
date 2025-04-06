package model;

import context.LocaleManager;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Where to add Column? Or relation?
 */

@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "id")
public class User extends Participant{

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<LocalizedUser> localizations = new HashSet<>();

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column
    private String defaultLanguage;

    public User() {}

    public User(String userName, String password, String email, String role, String phoneNumber, Date joinDate, String defaultLanguage) {
        super(phoneNumber, joinDate);
        this.password = password;
        this.email = email;
        this.role = role;
        this.defaultLanguage = defaultLanguage;
        addLocalization(defaultLanguage, userName);
    }

    public void addLocalization(String language, String userName) {
        LocaleManager lb = LocaleManager.getInstance();
        ResourceBundle resourceBundle = lb.getBundle();
        LocalizedUser localization = new LocalizedUser(language, userName, resourceBundle.getString(role));
        localizations.add(localization);
        localization.setUsers(this);
    }

    public void removeLocalization(LocalizedUser localization) {
        localizations.remove(localization);
        localization.setUsers(null);
    }

    public Optional<LocalizedUser> getLocalization(String language) {
        return localizations.stream().filter(l -> l.getLanguage().equals(language)).findFirst();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public String getAnyUserName() {
        if (localizations.stream().findFirst().isPresent()) {
            return localizations.stream().findFirst().get().getUserName();
        }
        return "No name";
    }
}
