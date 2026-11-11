package com.app.account_service.services;

import com.app.account_service.entities.PremiumTransaction;
import com.app.account_service.utils.EmailTemplates;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String SENDER_NAME = "diary-app";

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void send(String to, String subject, String text) {
        deliver(to, subject, text, false);
    }

    public void sendCode(String to, String title, String code, long ttlMinutes) {
        deliver(to, title + " | diary-app", EmailTemplates.verificationCode(title, code, ttlMinutes), true);
    }

    public void sendWelcome(String to, String firstname) {
        deliverQuietly(to, "Welcome to diary-app", EmailTemplates.welcome(firstname));
    }

    public void sendReceipt(String to, PremiumTransaction transaction) {
        deliverQuietly(to, "Your receipt from diary-app", EmailTemplates.receipt(transaction));
    }

    public void sendPasswordChanged(String to, String firstname) {
        deliverQuietly(to, "Your password has been changed", EmailTemplates.passwordChanged(firstname, Instant.now()));
    }

    public void sendSubscriptionCancelled(String to, String firstname, Instant expiresAt) {
        deliverQuietly(to, "Your subscription has been cancelled", EmailTemplates.subscriptionCancelled(firstname, expiresAt));
    }

    /** Для уведомлений: если отправка не удалась, основное действие (регистрация, покупка) не должно ломаться. */
    private void deliverQuietly(String to, String subject, String html) {
        try {
            deliver(to, subject, html, true);
        } catch (RuntimeException e) {
            log.warn("Failed to send email '{}'", subject, e);
        }
    }

    private void deliver(String to, String subject, String body, boolean html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from, SENDER_NAME);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, html);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new IllegalStateException("Failed to send email", e);
        }
    }
}
