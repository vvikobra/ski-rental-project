package vvikobra.miit.skirental.models.entities;

import jakarta.persistence.*;
import vvikobra.miit.skirental.models.enums.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    private Booking booking;

    private PaymentStatus status;

    private Double amount;

    private LocalDateTime paidAt;

    public Payment(Booking booking, PaymentStatus status, double amount, LocalDateTime paidAt) {
        this.booking = booking;
        this.status = status;
        this.amount = amount;
        this.paidAt = paidAt;
    }

    protected Payment() {}

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Column(nullable = false)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column
    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
