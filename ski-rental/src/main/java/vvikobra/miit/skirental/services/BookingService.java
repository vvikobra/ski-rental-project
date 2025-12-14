package vvikobra.miit.skirental.services;

import com.example.skirentalcontracts.api.dto.booking.BookingRequest;
import com.example.skirentalcontracts.api.dto.booking.BookingResponse;

import java.util.UUID;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBooking(UUID id);
    BookingResponse cancelBooking(UUID id);
}
