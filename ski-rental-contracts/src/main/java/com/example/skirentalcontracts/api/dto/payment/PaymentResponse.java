package com.example.skirentalcontracts.api.dto.payment;

import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.UUID;

@Relation(collectionRelation = "payments", itemRelation = "payment")
public class PaymentResponse extends RepresentationModel<PaymentResponse> {

    private final UUID bookingId;
    private final String status;
    private final SkipassResponse skipass;
    private final LocalDateTime paidAt;

    public PaymentResponse(UUID bookingId,
                           String status,
                           SkipassResponse skipass,
                           LocalDateTime paidAt) {
        this.bookingId = bookingId;
        this.status = status;
        this.skipass = skipass;
        this.paidAt = paidAt;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public String getStatus() {
        return status;
    }

    public SkipassResponse getSkipass() {
        return skipass;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}