package com.app.account_service.dtos.responses;

import com.app.account_service.entities.PremiumTransactionSource;
import com.app.account_service.entities.PremiumTransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PremiumTransactionResponse(
        Long id,
        PremiumTransactionSource source,
        PremiumTransactionStatus status,
        Short periodDays,
        Instant oldExpiresAt,
        Instant newExpiresAt,
        BigDecimal amountUsd,
        BigDecimal amountLocal,
        String currencySymbol,
        Instant createdAt,
        Long performedById,
        String performedByName
) { }
