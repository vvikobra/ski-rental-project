package vvikobra.miit.skirental.services;

import com.example.skirentalcontracts.api.dto.payment.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse getPayment(UUID bookingId);
    PaymentResponse payBooking(UUID id);

}
