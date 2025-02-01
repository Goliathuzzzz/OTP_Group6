package model;

import jakarta.persistence.*;
import jakarta.persistence.InheritanceType;

import java.util.Date;

@Entity
@Table(name = "guests")
public class Guest extends Participant {
    private Date deleteDate;

    public Guest(String guestPhone) {
        super(guestPhone);
    }

    public Guest() {

    }
}
