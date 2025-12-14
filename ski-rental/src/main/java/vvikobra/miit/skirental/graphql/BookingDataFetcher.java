package vvikobra.miit.skirental.graphql;

import com.example.skirentalcontracts.api.dto.booking.BookingRequest;
import com.example.skirentalcontracts.api.dto.booking.BookingResponse;
import com.example.skirentalcontracts.api.dto.skipass.SkipassRequest;
import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;
import vvikobra.miit.skirental.services.BookingService;

import java.time.LocalDateTime;
import java.util.*;

@DgsComponent
public class BookingDataFetcher {

    private final BookingService bookingService;

    @Autowired
    public BookingDataFetcher(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @DgsQuery
    public BookingResponse bookingById(@InputArgument("id") UUID id) {
        return bookingService.getBooking(id);
    }

    @DgsMutation
    public BookingResponse createBooking(@InputArgument("input") Map<String, Object> input) {

        UUID userId = UUID.fromString((String) input.get("userId"));
        LocalDateTime timeFrom = LocalDateTime.parse((String) input.get("timeFrom"));
        LocalDateTime timeTo = LocalDateTime.parse((String) input.get("timeTo"));

        List<String> itemsRaw = (List<String>) input.get("equipmentItems");
        List<UUID> equipmentItems = itemsRaw != null
                ? itemsRaw.stream().map(UUID::fromString).toList()
                : List.of();

        Map<String, Object> skipassMap = (Map<String, Object>) input.get("skipass");
        SkipassRequest skipass = null;
        if (skipassMap != null) {
            String type = (String) skipassMap.get("type");
            Integer liftsCount = skipassMap.get("liftsCount") != null
                    ? ((Number) skipassMap.get("liftsCount")).intValue()
                    : null;

            skipass = new SkipassRequest(type, liftsCount);
        }

        BookingRequest request = new BookingRequest(userId, equipmentItems, timeFrom, timeTo, skipass);
        return bookingService.createBooking(request);
    }

    @DgsMutation
    public BookingResponse cancelBooking(@InputArgument("id") UUID id) {
        return bookingService.cancelBooking(id);
    }
}
