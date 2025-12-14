package vvikobra.miit.skirental.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BookingRepository {
    long countByUserId(UUID userId);
    void cancelBooking(UUID bookingId, LocalDateTime cancelledAt);
}
