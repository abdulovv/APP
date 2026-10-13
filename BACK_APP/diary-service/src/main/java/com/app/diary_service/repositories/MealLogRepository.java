package com.app.diary_service.repositories;

import com.app.diary_service.entities.MealLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Long> {
    @EntityGraph(attributePaths = "items")
    List<MealLog> findAllByDiaryUserIdOrderByEatenAtDesc(Long userId);

    @EntityGraph(attributePaths = "items")
    List<MealLog> findAllByDiaryUserIdAndEatenAtBetweenOrderByEatenAtAsc(
            Long userId,
            LocalDateTime from,
            LocalDateTime to
    );

    Optional<MealLog> findByIdAndDiaryUserId(Long id, Long userId);
}
