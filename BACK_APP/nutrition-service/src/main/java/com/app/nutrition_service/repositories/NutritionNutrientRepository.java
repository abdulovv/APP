package com.app.nutrition_service.repositories;

import com.app.nutrition_service.entities.NutritionNutrient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionNutrientRepository extends JpaRepository<NutritionNutrient, Long> {
    List<NutritionNutrient> findByNutritionId(Long nutritionId);

    void deleteByNutritionId(Long nutritionId);
}
