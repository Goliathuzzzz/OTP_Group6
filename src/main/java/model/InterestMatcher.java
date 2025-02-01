package model;

import java.util.ArrayList;
import java.util.Objects;

public class InterestMatcher {
    private Session session;
    private final ArrayList<Match> matches;

    public InterestMatcher(Session session) {
        this.session = session;
        this.matches = new ArrayList<>();
    }

    public void matchParticipants() {
        ArrayList<Participant> participants = session.getParticipants();
        for (Participant p: participants) {
            for (int interest: p.interests) {
                Participant potentialMatch = session.getParticipantBySessionNumber(interest);
                for (int i: potentialMatch.interests) {
                    if (i == interest) {
                        for (Match m: matches) {
                            if (!(m.getParticipant1().equals(p) && m.getParticipant2().equals(potentialMatch) || m.getParticipant2().equals(p) && m.getParticipant1().equals(potentialMatch))) {
                                matches.add(new Match(session, p, potentialMatch));
                            }
                        }
                    }
                }
            }
        }
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
