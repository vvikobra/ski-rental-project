package vvikobra.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public record SkipassUsedEvent(
        String skipassId,
        LocalDateTime usedAt
) implements Serializable {
}