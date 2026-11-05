package com.app.account_service.services;

import com.app.account_service.dtos.requests.GrantPremiumRequest;
import com.app.account_service.entities.*;
import com.app.account_service.exceptions.UserNotFoundException;
import com.app.account_service.repositories.PremiumSubscriptionRepository;
import com.app.account_service.repositories.PremiumTransactionRepository;
import com.app.account_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PremiumService {
    private final UserRepository userRepository;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;
    private final PremiumTransactionRepository premiumTransactionRepository;

    @Transactional(readOnly = true)
    public boolean isPremiumActive(Long userId) {
        Instant now = Instant.now();
        return premiumSubscriptionRepository.findByUserId(userId)
                .map(subscription -> subscription.isActiveAt(now))
                .orElse(false);
    }

    @Transactional
    public void grantPremium(Long adminId, GrantPremiumRequest request) {
        User admin = userRepository.findById(adminId).orElseThrow(UserNotFoundException::new);
        User user = userRepository.findById(request.userId()).orElseThrow(UserNotFoundException::new);

        Instant now = Instant.now();
        PremiumSubscription subscription = premiumSubscriptionRepository.findByUserId(user.getId()).orElse(null);

        Instant oldExpiresAt = subscription != null ? subscription.getExpiresAt() : null;
        boolean isActive = subscription != null && subscription.isActiveAt(now);
        Instant extendFrom = isActive ? oldExpiresAt : now;
        Instant newExpiresAt = extendFrom.plus(request.days(), ChronoUnit.DAYS);

        if (subscription == null) {
            subscription = PremiumSubscription.builder()
                    .user(user)
                    .expiresAt(newExpiresAt)
                    .build();
        } else {
            if (!isActive) {
                subscription.setStartedAt(now);
            }
            subscription.setExpiresAt(newExpiresAt);
        }
        premiumSubscriptionRepository.save(subscription);

        Country country = user.getCountry();
        premiumTransactionRepository.save(PremiumTransaction.builder()
                .user(user)
                .country(country)
                .amountUsd(BigDecimal.ZERO)
                .currencySymbol(country.getCurrencySymbol())
                .exchangeRate(country.getExchangeRate())
                .amountLocal(BigDecimal.ZERO)
                .periodDays(request.days())
                .source(PremiumTransactionSource.ADMIN)
                .status(PremiumTransactionStatus.GRANTED)
                .oldExpiresAt(oldExpiresAt)
                .newExpiresAt(newExpiresAt)
                .performedBy(admin)
                .build());
    }
}
