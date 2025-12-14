package com.example.skirentalcontracts.api.exception;

import java.util.UUID;

public class PaymentTimeoutException extends RuntimeException {
    public PaymentTimeoutException(UUID bookingId) {
        super("Payment window expired for booking with ID: " + bookingId);
    }
}
