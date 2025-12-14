package com.example.skirentalcontracts.api.dto.equipment;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EquipmentRequest(
        @NotNull LocalDateTime timeFrom,
        @NotNull LocalDateTime timeTo,
        String type,
        Double height,
        Double weight,
        String skillLevel,
        Integer shoeSize
) {}
