package model;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String phoneNumber;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date joinDate;

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
}
