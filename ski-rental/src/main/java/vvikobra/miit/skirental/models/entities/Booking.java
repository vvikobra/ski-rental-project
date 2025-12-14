package vvikobra.miit.skirental.models.entities;

import jakarta.persistence.*;
import vvikobra.miit.skirental.models.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    private User user;

    private BookingStatus status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;

    private Set<Equipment> equipmentItems;

    private Payment payment;

    private Skipass skipass;

    public Booking(User user, BookingStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        this.user = user;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = LocalDateTime.now();
        this.equipmentItems = new HashSet<>();
    }

    protected Booking() {
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Column(nullable = false)
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Column(nullable = false)
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Column(nullable = false)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column
    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    @ManyToMany
    @JoinTable(
            name = "booking_items",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    public Set<Equipment> getEquipmentItems() {
        return equipmentItems;
    }

    public void setEquipmentItems(Set<Equipment> equipmentItems) {
        this.equipmentItems = equipmentItems;
    }

    @OneToOne(mappedBy = "booking", targetEntity = Payment.class)
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @OneToOne(mappedBy = "booking", targetEntity = Skipass.class)
    public Skipass getSkipass() {
        return skipass;
    }

    public void setSkipass(Skipass skipass) {
        this.skipass = skipass;
    }
}
