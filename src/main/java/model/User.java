package model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Where to add Column? Or relation?
 */

@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "id")
public class User extends Participant{

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "participant1", cascade = CascadeType.ALL)
    private Set<Match> matchesAsFirst;

    @OneToMany(mappedBy = "participant2", cascade = CascadeType.ALL) //ensure if a participant is deleted, all their matches are also deleted
    private Set<Match> matchesAsSecond;

    public User() {}

    public User(String userName, String password, String email, String role, String phoneNumber) {
        super(phoneNumber);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Set<Match> getMatchesAsFirst() {
        return matchesAsFirst;
    }

    public void setMatchesAsFirst(Set<Match> matchesAsFirst) {
        this.matchesAsFirst = matchesAsFirst;
    }

    public Set<Match> getMatchesAsSecond() {
        return matchesAsSecond;
    }

    public void setMatchesAsSecond(Set<Match> matchesAsSecond) {
        this.matchesAsSecond = matchesAsSecond;
    }
}
