package com.example.skirentalcontracts.api.dto.booking;

import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;
import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Relation(collectionRelation = "bookings", itemRelation = "booking")
public class BookingResponse extends RepresentationModel<BookingResponse> {

    private final UUID bookingId;
    private final UUID userId;
    private final List<EquipmentResponse> equipmentItems;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final SkipassResponse skiPass;
    private final String status;
    private final String paymentStatus;
    private final Double totalCost;
    private final LocalDateTime createdAt;
    private final LocalDateTime cancelledAt;

    public BookingResponse(UUID bookingId,
                           UUID userId,
                           List<EquipmentResponse> equipmentItems,
                           LocalDateTime startDate,
                           LocalDateTime endDate,
                           SkipassResponse skiPass,
                           String status,
                           String paymentStatus,
                           Double totalCost,
                           LocalDateTime createdAt,
                           LocalDateTime cancelledAt) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.equipmentItems = equipmentItems;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skiPass = skiPass;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
        this.cancelledAt = cancelledAt;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<EquipmentResponse> getEquipmentItems() {
        return equipmentItems;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public SkipassResponse getSkiPass() {
        return skiPass;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookingResponse that = (BookingResponse) o;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(userId, that.userId) && Objects.equals(equipmentItems, that.equipmentItems) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(skiPass, that.skiPass) && Objects.equals(status, that.status) && Objects.equals(paymentStatus, that.paymentStatus) && Objects.equals(totalCost, that.totalCost) && Objects.equals(createdAt, that.createdAt) && Objects.equals(cancelledAt, that.cancelledAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bookingId, userId, equipmentItems, startDate, endDate, skiPass, status, paymentStatus, totalCost, createdAt, cancelledAt);
    }
}