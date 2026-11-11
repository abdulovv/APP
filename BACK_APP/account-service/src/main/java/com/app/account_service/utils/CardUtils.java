package com.app.account_service.utils;

import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Random;
import java.util.regex.Pattern;

public final class CardUtils {
    private static final Pattern CARD_NUMBER = Pattern.compile("^[0-9]{16}$");
    private static final Pattern MASTERCARD = Pattern.compile("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720).*");
    private static final Pattern MIR = Pattern.compile("^220[0-4].*");

    private CardUtils() {
    }

    public static String normalizeNumber(String number) {
        return number.replace(" ", "");
    }

    public static boolean isValidNumber(String normalizedNumber) {
        return CARD_NUMBER.matcher(normalizedNumber).matches();
    }

    public static String last4(String number) {
        return number.substring(number.length() - 4);
    }

    public static String detectBrand(String number) {
        if (number.startsWith("4")) return "Visa";
        if (MASTERCARD.matcher(number).matches()) return "Mastercard";
        if (number.startsWith("34") || number.startsWith("37")) return "Amex";
        if (MIR.matcher(number).matches()) return "Mir";

        int num = LocalTime.now().getSecond();
        return (num % 2 == 0) ? "Visa" : "Mastercard";
    }

    public static boolean isExpired(int expMonth, int expYear) {
        return YearMonth.of(expYear, expMonth).isBefore(YearMonth.now());
    }
}
