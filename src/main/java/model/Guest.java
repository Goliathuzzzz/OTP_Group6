package model;

import jakarta.persistence.*;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "guests")
public class Guest {
    @Id
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
