package com.app.account_service.services;

import com.app.account_service.dtos.requests.PurchasePremiumRequest;
import com.app.account_service.dtos.responses.PremiumTransactionResponse;
import com.app.account_service.exceptions.CardNotFoundException;
import com.app.account_service.exceptions.InvalidPremiumActionException;
import com.app.account_service.exceptions.InvalidPurchaseException;
import com.app.account_service.exceptions.UserNotFoundException;
import com.app.account_service.repositories.PaymentCardRepository;
import com.app.account_service.entities.*;
import com.app.account_service.mappers.PremiumTransactionMapper;
import com.app.account_service.repositories.PremiumSubscriptionRepository;
import com.app.account_service.repositories.PremiumTransactionRepository;
import com.app.account_service.repositories.UserRepository;
import com.app.account_service.utils.CardUtils;
import com.app.account_service.utils.PremiumPricing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PremiumService {
    private final UserRepository userRepository;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;
    private final PremiumTransactionRepository premiumTransactionRepository;
    private final PremiumTransactionMapper premiumTransactionMapper;
    private final PaymentCardRepository paymentCardRepository;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public boolean isPremiumActive(Long userId) {
        Instant now = Instant.now();
        return premiumSubscriptionRepository.findByUserId(userId)
                .map(subscription -> subscription.isActiveAt(now))
                .orElse(false);
    }

    public PremiumSubscription getPremium(Long userId) {
        return premiumSubscriptionRepository.findByUserId(userId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PremiumTransactionResponse> getHistory(Long userId) {
        List<PremiumTransaction> transactions =
                premiumTransactionRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        return premiumTransactionMapper.toResponseList(transactions);
    }

    @Transactional
    public void purchase(Long userId, PurchasePremiumRequest request) {
        BigDecimal pricePerMonth = PremiumPricing.pricePerMonth(request.months());
        if (pricePerMonth == null) {
            throw new InvalidPurchaseException("Unknown plan");
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        PaymentCard card = paymentCardRepository.findByIdAndUserId(request.cardId(), userId)
                .orElseThrow(CardNotFoundException::new);
        if (CardUtils.isExpired(card.getExpMonth(), card.getExpYear())) {
            throw new InvalidPurchaseException("Card has expired");
        }

        Instant now = Instant.now();
        PremiumSubscription subscription = premiumSubscriptionRepository.findByUserId(userId).orElse(null);
        if (subscription != null && subscription.isActiveAt(now)) {
            throw new InvalidPurchaseException("You already have active Premium. You can buy a new plan after it ends");
        }
        Instant oldExpiresAt = subscription != null ? subscription.getExpiresAt() : null;

        int days = PremiumPricing.days(request.months());
        Instant newExpiresAt = now.plus(days, ChronoUnit.DAYS);

        if (subscription == null) {
            subscription = PremiumSubscription.builder().user(user).expiresAt(newExpiresAt).build();
        } else {
            subscription.setStartedAt(now);
            subscription.setExpiresAt(newExpiresAt);
            subscription.setCancelledAt(null);
        }
        premiumSubscriptionRepository.save(subscription);

        Country country = user.getCountry();
        BigDecimal amountUsd = PremiumPricing.totalUsd(pricePerMonth, request.months());
        BigDecimal amountLocal = PremiumPricing.toLocal(amountUsd, country.getExchangeRate());

        PremiumTransaction transaction = premiumTransactionRepository.save(PremiumTransaction.builder()
                .user(user)
                .paymentCard(card)
                .cardBrand(card.getBrand())
                .cardLast4(card.getLast4())
                .country(country)
                .amountUsd(amountUsd)
                .currencySymbol(country.getCurrencySymbol())
                .exchangeRate(country.getExchangeRate())
                .amountLocal(amountLocal)
                .periodDays((short) days)
                .source(PremiumTransactionSource.PURCHASE)
                .status(PremiumTransactionStatus.PAID)
                .oldExpiresAt(oldExpiresAt)
                .newExpiresAt(newExpiresAt)
                .performedBy(user)
                .build());

        emailService.sendReceipt(user.getEmail(), transaction);
    }

    @Transactional
    public void cancel(Long userId) {
        PremiumSubscription subscription = premiumSubscriptionRepository.findByUserId(userId)
                .filter(s -> s.isActiveAt(Instant.now()))
                .orElseThrow(() -> new InvalidPremiumActionException("You don't have active Premium"));
        if (subscription.getCancelledAt() != null) {
            throw new InvalidPremiumActionException("Subscription is already cancelled");
        }
        subscription.setCancelledAt(Instant.now());
        premiumSubscriptionRepository.save(subscription);

        User user = subscription.getUser();
        emailService.sendSubscriptionCancelled(user.getEmail(), user.getFirstname(), subscription.getExpiresAt());
    }
}
