package vvikobra.events;

import java.io.Serializable;

public record BookingPaidEvent(
        String bookingId
) implements Serializable {
}
