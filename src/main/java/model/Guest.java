package model;

import jakarta.persistence.*;
import jakarta.persistence.InheritanceType;

/**
 * Where to add Column? Or relation?
 */

@Entity
@Table(name = "guests")
public class Guest extends Participant{

    public Guest(String guestPhone) {
        super(guestPhone);
    }

    public Guest() {

    }
}
