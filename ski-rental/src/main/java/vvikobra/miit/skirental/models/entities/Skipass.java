package vvikobra.miit.skirental.models.entities;

import jakarta.persistence.*;
import vvikobra.miit.skirental.models.enums.SkipassType;

import java.time.LocalDateTime;

@Entity
@Table(name = "skipasses")
public class Skipass extends BaseEntity {
    private Booking booking;

    private SkipassType type;

    private LocalDateTime validFrom;
    private LocalDateTime validUntil;

    private Integer totalLifts;
    private Integer remainingLifts;
    private boolean isActive;

    public Skipass(Booking booking, SkipassType type, LocalDateTime validFrom, LocalDateTime validUntil, Integer totalLifts) {
        this.booking = booking;
        this.type = type;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.totalLifts = totalLifts;
        this.remainingLifts = totalLifts;
        this.isActive = false;
    }

    protected Skipass() {
    }

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    public SkipassType getType() {
        return type;
    }

    public void setType(SkipassType type) {
        this.type = type;
    }

    @Column
    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    @Column
    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    @Column
    public Integer getTotalLifts() {
        return totalLifts;
    }

    public void setTotalLifts(Integer totalLifts) {
        this.totalLifts = totalLifts;
    }

    @Column
    public Integer getRemainingLifts() {
        return remainingLifts;
    }

    public void setRemainingLifts(Integer remainingLifts) {
        this.remainingLifts = remainingLifts;
    }

    @Column
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
