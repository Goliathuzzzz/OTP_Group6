package model;

import jakarta.persistence.*;

import java.util.Date;
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

    public Guest(String phoneNumber, Date joinDate) {
        super(phoneNumber, joinDate);
    }

    public Guest() {}
}
