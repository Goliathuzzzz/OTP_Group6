package model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "language"}), name = "user_localizations")
public class LocalizedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User users;

    @Column(nullable = false, length = 10)
    private String language;

    private String userName;
    private String roleTranslation;

    public LocalizedUser(String language, String userName, String roleTranslation) {
        this.language = language;
        this.userName = userName;
        this.roleTranslation = roleTranslation;
    }

    public LocalizedUser() {

    }

    public User getUser() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public String getLanguage() {
        return language;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoleTranslation(String roleTranslation) {
        this.roleTranslation = roleTranslation;
    }

    public String getRoleTranslation() {
        return roleTranslation;
    }
}
