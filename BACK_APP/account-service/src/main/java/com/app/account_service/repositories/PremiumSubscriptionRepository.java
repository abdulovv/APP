package com.app.account_service.repositories;

import com.app.account_service.entities.PremiumSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface PremiumSubscriptionRepository extends JpaRepository<PremiumSubscription, Long> {
    Optional<PremiumSubscription> findByUserId(Long userId);
}
