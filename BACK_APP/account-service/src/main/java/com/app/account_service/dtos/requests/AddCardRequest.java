package com.app.account_service.dtos.requests;

import jakarta.validation.constraints.*;

public record AddCardRequest(
        @NotBlank
        @Pattern(regexp = "^[0-9]{4}( ?[0-9]{4}){3}$", message = "Card number must contain 16 digits")
        String cardNumber,

        @NotBlank
        @Size(max = 100)
        String holderName,

        @NotNull
        @Min(1)
        @Max(12)
        Short expMonth,

        @NotNull
        @Min(2000)
        @Max(2100)
        Short expYear,

        @NotBlank
        @Pattern(regexp = "^[0-9]{3,4}$", message = "CVC must contain 3 or 4 digits")
        String cvc,

        boolean makeDefault
) {
}
