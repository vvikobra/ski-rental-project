package vvikobra.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public record SkipassActivatedEvent(
        String skipassId,
        String type,
        Integer totalLifts,
        LocalDateTime validFrom,
        LocalDateTime validUntil
) implements Serializable {
}
