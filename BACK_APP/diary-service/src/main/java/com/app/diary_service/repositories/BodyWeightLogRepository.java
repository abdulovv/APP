package com.app.diary_service.repositories;

import com.app.diary_service.entities.BodyWeightLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyWeightLogRepository extends JpaRepository<BodyWeightLog, Long> {
    List<BodyWeightLog> findAllByDiaryUserIdOrderByCreatedAtDesc(Long userId);
}
