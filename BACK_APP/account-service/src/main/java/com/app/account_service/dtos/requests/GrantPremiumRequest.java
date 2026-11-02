package com.app.account_service.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GrantPremiumRequest(
        @NotNull
        Long userId,

        @NotNull
        @Min(1)
        @Max(3650)
        Short days
) {
}
