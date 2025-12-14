package vvikobra.miit.skirental.controllers;

import com.example.skirentalcontracts.api.dto.payment.PaymentResponse;
import com.example.skirentalcontracts.api.endpoints.PaymentApi;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RestController;
import vvikobra.miit.skirental.assemblers.PaymentModelAssembler;
import vvikobra.miit.skirental.services.PaymentService;

import java.util.UUID;

@RestController
public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;
    private final PaymentModelAssembler assembler;

    public PaymentController(PaymentService paymentService, PaymentModelAssembler assembler) {
        this.paymentService = paymentService;
        this.assembler = assembler;
    }

    @Override
    public EntityModel<PaymentResponse> getPayment(UUID bookingId) {
        return assembler.toModel(paymentService.getPayment(bookingId));
    }

    @Override
    public EntityModel<PaymentResponse> payBooking(UUID bookingId) {
        return assembler.toModel(paymentService.payBooking(bookingId));
    }
}