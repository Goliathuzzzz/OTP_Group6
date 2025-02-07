package model;

import jakarta.persistence.*;

// Matchiin tallennetaan sessionin sijasta nyt yhteensopivuus-prosentti
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "participant1_id", nullable = false)
    private Participant participant1;

    @ManyToOne
    @JoinColumn(name = "participant2_id", nullable = false)
    private Participant participant2;

    @Column(name = "compatibility", nullable = false)
    private double compatibility;

    public Match(Participant participant1, Participant participant2, double compatibility) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.compatibility = compatibility;
    }

    public Match() {}

    public Long getMatchId() {
        return matchId;
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

    public double getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(double compatibility) {
        this.compatibility = compatibility;
    }
}
