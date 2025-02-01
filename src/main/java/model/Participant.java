package model;

import jakarta.persistence.*;

import java.util.Date;

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

    public Participant() {}

    public Participant(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
