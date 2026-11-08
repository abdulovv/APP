package com.app.account_service.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PurchasePremiumRequest(
        @NotNull
        Long cardId,

        @NotNull
        @Min(1)
        Integer months
) {
}
