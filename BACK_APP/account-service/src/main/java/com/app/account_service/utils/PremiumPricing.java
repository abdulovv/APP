package com.app.account_service.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public final class PremiumPricing {
    public static final int DAYS_PER_MONTH = 30;

    private static final Map<Integer, BigDecimal> PRICE_PER_MONTH = Map.of(
            1, new BigDecimal("4.99"),
            6, new BigDecimal("3.99"),
            12, new BigDecimal("2.99")
    );

    private PremiumPricing() {
    }

    public static BigDecimal pricePerMonth(int months) {
        return PRICE_PER_MONTH.get(months);
    }

    public static BigDecimal totalUsd(BigDecimal pricePerMonth, int months) {
        return pricePerMonth.multiply(BigDecimal.valueOf(months));
    }

    public static BigDecimal toLocal(BigDecimal amountUsd, double exchangeRate) {
        return amountUsd.multiply(BigDecimal.valueOf(exchangeRate)).setScale(2, RoundingMode.HALF_UP);
    }

    public static int days(int months) {
        return months * DAYS_PER_MONTH;
    }
}
