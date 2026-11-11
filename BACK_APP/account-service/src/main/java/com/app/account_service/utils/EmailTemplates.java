package com.app.account_service.utils;

import com.app.account_service.entities.PremiumTransaction;

import java.time.Instant;
import java.util.Map;

public final class EmailTemplates {
    private EmailTemplates() {
    }

    public static String receipt(PremiumTransaction transaction) {
        return TemplateRenderer.render("receipt.html", Map.of(
                "amount", transaction.getAmountLocal().toPlainString() + " " + transaction.getCurrencySymbol(),
                "plan", "Premium, " + transaction.getPeriodDays() + " days",
                "card", transaction.getCardBrand() + " •••• " + transaction.getCardLast4(),
                "expiresAt", DateUtils.formatLongDate(transaction.getNewExpiresAt())
        ));
    }

    public static String welcome(String firstname) {
        return TemplateRenderer.render("welcome.html", Map.of("name", firstname));
    }

    public static String subscriptionCancelled(String firstname, Instant expiresAt) {
        return TemplateRenderer.render("cancelled.html", Map.of(
                "name", firstname,
                "expiresAt", DateUtils.formatLongDate(expiresAt)
        ));
    }

    public static String passwordChanged(String firstname, Instant changedAt) {
        return TemplateRenderer.render("password-changed.html", Map.of(
                "name", firstname,
                "date", DateUtils.formatLongDate(changedAt)
        ));
    }

    public static String verificationCode(String title, String code, long ttlMinutes) {
        return TemplateRenderer.render("code.html", Map.of(
                "title", title,
                "code", code,
                "minutes", String.valueOf(ttlMinutes)
        ));
    }
}
