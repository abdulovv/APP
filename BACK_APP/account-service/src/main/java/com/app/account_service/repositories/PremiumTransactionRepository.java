package com.app.account_service.repositories;

import com.app.account_service.entities.PremiumTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PremiumTransactionRepository extends JpaRepository<PremiumTransaction, Long> {
    List<PremiumTransaction> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
