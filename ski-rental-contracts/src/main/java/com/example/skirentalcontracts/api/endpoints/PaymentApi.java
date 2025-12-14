package com.example.skirentalcontracts.api.endpoints;

import com.example.skirentalcontracts.api.dto.payment.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Tag(name = "payments", description = "API для обработки платежей бронирований")
public interface PaymentApi {

    @Operation(summary = "Получить информацию о платеже по бронированию")
    @GetMapping("/api/bookings/{id}/payment")
    EntityModel<PaymentResponse> getPayment(@PathVariable("id") UUID bookingId);

    @Operation(summary = "Оплатить бронирование")
    @PostMapping("/api/bookings/{id}/payment")
    EntityModel<PaymentResponse> payBooking(@PathVariable("id") UUID bookingId);
}
