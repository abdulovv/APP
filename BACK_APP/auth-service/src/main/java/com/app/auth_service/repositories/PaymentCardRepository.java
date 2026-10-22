package com.app.auth_service.repositories;

import com.app.auth_service.entities.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    List<PaymentCard> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    // Ищет карту только среди карт этого пользователя (чужую не отдаст)
    Optional<PaymentCard> findByIdAndUserId(Long id, Long userId);

    Optional<PaymentCard> findByUserIdAndIsDefaultTrue(Long userId);

    boolean existsByUserIdAndBrandAndLast4AndExpMonthAndExpYear(
            Long userId, String brand, String last4, Integer expMonth, Integer expYear);

    long countByUserId(Long userId);
}
