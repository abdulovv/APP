package com.app.account_service.services;

import com.app.account_service.dtos.requests.GrantPremiumRequest;
import com.app.account_service.dtos.responses.AdminUserDetailsResponse;
import com.app.account_service.dtos.responses.AdminUserListItemResponse;
import com.app.account_service.dtos.responses.PremiumTransactionResponse;
import com.app.account_service.entities.*;
import com.app.account_service.exceptions.UserNotFoundException;
import com.app.account_service.mappers.UserMapper;
import com.app.account_service.repositories.PremiumSubscriptionRepository;
import com.app.account_service.repositories.PremiumTransactionRepository;
import com.app.account_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private static final int SEARCH_RESULT_LIMIT = 20;
    private final UserRepository userRepository;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;
    private final PremiumService premiumService;
    private final UserMapper userMapper;
    private final PremiumTransactionRepository premiumTransactionRepository;


    @Transactional(readOnly = true)
    public List<AdminUserListItemResponse> searchUsers(String query) {
        String normalizedQuery = query == null ? "" : query.trim();
        List<User> users = userRepository.searchByNameEmailOrPhone(
                normalizedQuery, PageRequest.of(0, SEARCH_RESULT_LIMIT));

        List<AdminUserListItemResponse> result = new ArrayList<>();
        for (User user : users) {
            boolean premium = premiumService.isPremiumActive(user.getId());
            result.add(userMapper.toAdminListItem(user, premium));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public AdminUserDetailsResponse getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        PremiumSubscription subscription = premiumSubscriptionRepository.findByUserId(userId).orElse(null);

        boolean premium = subscription != null && subscription.isActiveAt(Instant.now());
        Instant premiumStartedAt = subscription != null ? subscription.getStartedAt() : null;
        Instant premiumExpiresAt = subscription != null ? subscription.getExpiresAt() : null;

        return userMapper.toAdminDetails(user, premium, premiumStartedAt, premiumExpiresAt);
    }

    @Transactional(readOnly = true)
    public List<PremiumTransactionResponse> getPremiumHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        return premiumService.getHistory(userId);
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
                subscription.setCancelledAt(null);
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
