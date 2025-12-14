package vvikobra.events;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record BookingCreatedEvent(
        String bookingId,
        List<String> equipmentTypes,
        LocalDateTime startDate,
        LocalDateTime endDate,
        double totalCost
) implements Serializable {
}
