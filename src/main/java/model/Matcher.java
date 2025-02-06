package model;

import java.util.ArrayList;

public class Matcher {
    private Session session;
    private final ArrayList<Match> matches;

    public Matcher(Session session) {
        this.session = session;
        this.matches = new ArrayList<>();
    }

    public void matchParticipants() {
        int compatibility;

    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
