package com.example.skirentalcontracts.api.dto.skipass;

import jakarta.validation.constraints.NotNull;

public record SkipassRequest(
        @NotNull String type,
        Integer liftsCount
) {
}
