package vvikobra.miit.skirental.assemblers;

import com.example.skirentalcontracts.api.dto.payment.PaymentResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import vvikobra.miit.skirental.controllers.PaymentController;
import vvikobra.miit.skirental.controllers.SkipassController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PaymentModelAssembler implements RepresentationModelAssembler<PaymentResponse, EntityModel<PaymentResponse>> {
    @Override
    public EntityModel<PaymentResponse> toModel(PaymentResponse payment) {
        return EntityModel.of(payment,
                linkTo(methodOn(PaymentController.class).getPayment(payment.getBookingId())).withSelfRel(),
                (linkTo(methodOn(SkipassController.class).getSkipass(payment.getSkipass().getId())).withRel("skipass"))
        );
    }
}