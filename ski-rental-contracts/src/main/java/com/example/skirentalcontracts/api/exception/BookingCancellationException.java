package com.example.skirentalcontracts.api.exception;

public class BookingCancellationException extends RuntimeException {
    public BookingCancellationException(String message) {
        super(message);
    }
}
