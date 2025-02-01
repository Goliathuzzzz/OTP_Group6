package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;

    @Column(name = "session_code", nullable = false, unique = true)
    private int sessionCode;

    @Column(nullable = false)
    private String status;

    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;

    @Column(nullable = false)
    private String type;

    @Column
    private String description;

    @OneToMany
    @Column
    private ArrayList<Participant> participants;

    public Session(int sessionId, int sessionCode, String status, LocalDateTime sessionDate, String type, String description) {
        this.sessionId = sessionId;
        this.sessionCode = sessionCode;
        this.status = status;
        this.sessionDate = sessionDate;
        this.type = type;
        this.description = description;
        participants = new ArrayList<>();
    }

    public Session() {}

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(int sessionCode) {
        this.sessionCode = sessionCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUpcoming() {
        return sessionDate.isAfter(LocalDateTime.now()) && "N".equalsIgnoreCase(status);
    }

    public boolean isActive() {
        return "Ongoing".equalsIgnoreCase(status);
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }
}
