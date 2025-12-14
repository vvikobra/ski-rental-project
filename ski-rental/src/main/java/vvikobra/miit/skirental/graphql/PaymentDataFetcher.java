package vvikobra.miit.skirental.graphql;

import com.example.skirentalcontracts.api.dto.payment.PaymentResponse;
import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;
import vvikobra.miit.skirental.services.PaymentService;

import java.util.UUID;

@DgsComponent
public class PaymentDataFetcher {

    private final PaymentService paymentService;

    @Autowired
    public PaymentDataFetcher(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @DgsMutation
    public PaymentResponse payBooking(@InputArgument("bookingId") UUID bookingId) {
        return paymentService.payBooking(bookingId);
    }
}
