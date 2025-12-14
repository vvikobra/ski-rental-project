package com.example.skirentalcontracts.api.exception;

import java.util.UUID;

public class PaymentAlreadyProcessedException extends RuntimeException {
  public PaymentAlreadyProcessedException(UUID bookingId) {
    super("Payment has already been processed for booking with ID: " + bookingId);
  }
}
