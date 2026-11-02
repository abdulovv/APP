package com.app.account_service.services;

import com.app.account_service.dtos.responses.AdminUserDetailsResponse;
import com.app.account_service.dtos.responses.AdminUserListItemResponse;
import com.app.account_service.entities.PremiumSubscription;
import com.app.account_service.entities.User;
import com.app.account_service.exceptions.UserNotFoundException;
import com.app.account_service.mappers.UserMapper;
import com.app.account_service.repositories.PremiumSubscriptionRepository;
import com.app.account_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private static final int SEARCH_RESULT_LIMIT = 20;

    private final UserRepository userRepository;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;
    private final PremiumService premiumService;
    private final UserMapper userMapper;

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


}
