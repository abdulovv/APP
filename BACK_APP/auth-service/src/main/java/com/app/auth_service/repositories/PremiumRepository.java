package com.app.auth_service.repositories;

import com.app.auth_service.entities.Premium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface PremiumRepository extends JpaRepository<Premium, Long> {
    Optional<Premium> findByUserId(Long userId);

    // true, если у пользователя premium действует на момент now
    boolean existsByUserIdAndExpiresAtAfter(Long userId, Instant now);
}
