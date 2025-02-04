package model;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Where to add Column? Or relation?
 */

@Entity
@Table(name = "guests")
@PrimaryKeyJoinColumn(name = "id")
public class Guest extends Participant{

    @OneToMany(mappedBy = "participant1", cascade = CascadeType.ALL)
    private Set<Match> matchesAsFirst;

    @OneToMany(mappedBy = "participant2", cascade = CascadeType.ALL)
    private Set<Match> matchesAsSecond;

    public Guest(String phoneNumber) {
        super(phoneNumber);
    }

    public Guest() {}


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
