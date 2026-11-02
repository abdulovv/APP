package com.app.account_service.repositories;

import com.app.account_service.entities.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    List<PaymentCard> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<PaymentCard> findByIdAndUserId(Long id, Long userId);

    Optional<PaymentCard> findByUserIdAndIsDefaultTrue(Long userId);

    boolean existsByUserIdAndBrandAndLast4AndExpMonthAndExpYear(
            Long userId, String brand, String last4, Short expMonth, Short expYear);

    long countByUserId(Long userId);
}
