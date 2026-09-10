package com.app.nutrition_service.repositories;

import com.app.nutrition_service.entities.CategoryType;
import com.app.nutrition_service.entities.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
    List<Nutrition> findByCategoryType(CategoryType type);

    List<Nutrition> findByCategoryNameAndCategoryType(String categoryName, CategoryType type);
}
