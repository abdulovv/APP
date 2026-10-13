package com.app.diary_service.repositories;

import com.app.diary_service.entities.MealLogsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealLogsInfoRepository extends JpaRepository<MealLogsInfo, Long> {

    List<MealLogsInfo> findAllByMealLogId(Long mealLogId);
}
