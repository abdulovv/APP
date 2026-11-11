package com.app.account_service.dtos.responses;

import com.app.account_service.entities.PaymentCard;

public record PaymentCardResponse(
        Long id,
        String brand,
        String last4,
        String holderName,
        Short expMonth,
        Short expYear,
        Boolean isDefault
) {
    public static PaymentCardResponse from(PaymentCard card) {
        return new PaymentCardResponse(
                card.getId(),
                card.getBrand(),
                card.getLast4(),
                card.getHolderName(),
                card.getExpMonth(),
                card.getExpYear(),
                card.getIsDefault()
        );
    }
}
