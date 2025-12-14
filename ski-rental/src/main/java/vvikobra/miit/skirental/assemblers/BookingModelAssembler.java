package vvikobra.miit.skirental.assemblers;

import com.example.skirentalcontracts.api.dto.booking.BookingResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import vvikobra.miit.skirental.controllers.BookingController;
import vvikobra.miit.skirental.controllers.PaymentController;
import vvikobra.miit.skirental.controllers.SkipassController;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BookingModelAssembler implements RepresentationModelAssembler<BookingResponse, EntityModel<BookingResponse>> {

    @Override
    public EntityModel<BookingResponse> toModel(BookingResponse booking) {
        UUID bookingId = booking.getBookingId();

        return EntityModel.of(booking,
                linkTo(methodOn(BookingController.class).getBooking(bookingId)).withSelfRel(),
                linkTo(methodOn(BookingController.class).cancelBooking(bookingId)).withRel("cancel"),
                linkTo(methodOn(PaymentController.class).payBooking(bookingId)).withRel("pay-booking"),
                linkTo(methodOn(SkipassController.class).getSkipass(bookingId)).withRel("skipass"),
                linkTo(methodOn(PaymentController.class).getPayment(bookingId)).withRel("payment")
        );
    }
}