package com.example.skirentalcontracts.api.dto.booking;

import com.example.skirentalcontracts.api.dto.skipass.SkipassRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BookingRequest(
        @NotNull UUID userId,
        @NotEmpty List<UUID> equipmentItems,
        @NotNull LocalDateTime timeFrom,
        @NotNull LocalDateTime timeTo,
        @NotNull SkipassRequest skipass
) {
}