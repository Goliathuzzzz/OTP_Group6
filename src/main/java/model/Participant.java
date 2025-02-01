package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(nullable = false)
    protected String phoneNumber;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date joinDate;
    @Column // This is the number assigned to each participant at live sessions
    protected int sessionNumber;
    @ManyToOne
    @JoinColumn
    protected Session activeSession;
    @ElementCollection
    protected List<Integer> interests;

    public Participant() {}

    public Participant(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        interests = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setActiveSession(Session activeSession) {
        this.activeSession = activeSession;
    }

    public Session getActiveSession() {
        return activeSession;
    }
}
