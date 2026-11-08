package com.app.account_service.dtos.responses;

public record PaymentCardResponse(
        Long id,
        String brand,
        String last4,
        String holderName,
        Short expMonth,
        Short expYear,
        Boolean isDefault
) {
}
