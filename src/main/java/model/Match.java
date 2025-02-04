package model;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "participant1_id", nullable = false)
    private Participant participant1;

    @ManyToOne
    @JoinColumn(name = "participant2_id", nullable = false)
    private Participant participant2;

    public Match(Session session, Participant participant1, Participant participant2) {
        this.session = session;
        this.participant1 = participant1;
        this.participant2 = participant2;
    }

    public Match() {}

    public Long getMatchId() {
        return matchId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Participant getParticipant1() {
        return participant1;
    }

    public void setParticipant1(Participant participant1) {
        this.participant1 = participant1;
    }

    public Participant getParticipant2() {
        return participant2;
    }

    public void setParticipant2(Participant participant2) {
        this.participant2 = participant2;
    }
}
