package vvikobra.miit.skirental.controllers;

import com.example.skirentalcontracts.api.dto.booking.BookingRequest;
import com.example.skirentalcontracts.api.dto.booking.BookingResponse;
import com.example.skirentalcontracts.api.endpoints.BookingApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vvikobra.miit.skirental.assemblers.BookingModelAssembler;
import vvikobra.miit.skirental.services.BookingService;

import java.util.UUID;

@RestController
public class BookingController implements BookingApi {

    private final BookingService bookingService;
    private final BookingModelAssembler assembler;

    @Autowired
    public BookingController(BookingService bookingService, BookingModelAssembler assembler) {
        this.bookingService = bookingService;
        this.assembler = assembler;
    }

    @Override
    public EntityModel<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        return assembler.toModel(bookingService.createBooking(request));
    }

    @Override
    public EntityModel<BookingResponse> getBooking(@PathVariable("id") UUID id) {
        return assembler.toModel(bookingService.getBooking(id));
    }

    @Override
    public EntityModel<BookingResponse> cancelBooking(@PathVariable("id") UUID id) {
        return assembler.toModel(bookingService.cancelBooking(id));
    }
}
