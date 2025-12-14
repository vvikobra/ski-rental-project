package vvikobra.miit.skirental.models.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import vvikobra.miit.skirental.models.entities.BaseEntity;
import vvikobra.miit.skirental.models.entities.Booking;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "booking_statuses")
public class BookingStatus extends BaseEntity {
    private String name;
    private Set<Booking> bookings;

    public BookingStatus(String name) {
        this.name = name;
        this.bookings = new HashSet<>();
    }

    protected BookingStatus() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "status", targetEntity = Booking.class)
    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
}
