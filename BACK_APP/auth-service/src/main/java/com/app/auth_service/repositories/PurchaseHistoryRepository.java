package com.app.auth_service.repositories;

import com.app.auth_service.entities.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    List<PurchaseHistory> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
