package vvikobra.miit.skirental.repositories;

import vvikobra.miit.skirental.models.entities.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Optional<Payment> findByBookingId(UUID bookingId);
}
