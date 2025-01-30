package model;

import java.time.LocalDateTime;

public class Session {
    private int sessionId;
    private int sessionCode;
    private String status;
    private LocalDateTime sessionDate;
    private String type;
    private String description;

    public Session() {}

    public Session(int sessionId, int sessionCode, String status, LocalDateTime sessionDate, String type, String description) {
        this.sessionId = sessionId;
        this.sessionCode = sessionCode;
        this.status = status;
        this.sessionDate = sessionDate;
        this.type = type;
        this.description = description;
    }

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
}
