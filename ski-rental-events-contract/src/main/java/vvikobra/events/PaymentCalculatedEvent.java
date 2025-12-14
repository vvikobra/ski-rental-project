package vvikobra.events;

import java.io.Serializable;

public record PaymentCalculatedEvent(
        double finalPrice
) implements Serializable {
}