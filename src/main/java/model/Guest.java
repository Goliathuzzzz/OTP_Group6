package model;

import jakarta.persistence.*;
import jakarta.persistence.InheritanceType;

/**
 * Where to add Column? Or relation?
 */

@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guestId;
    private String guestPhone;

    public Guest() {}

    public Guest(int guestId, String guestPhone) {
        this.guestId = guestId;
        this.guestPhone = guestPhone;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }
}
