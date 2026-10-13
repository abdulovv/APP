package com.app.nutrition_service.repositories;

import com.app.nutrition_service.entities.CategoryType;
import com.app.nutrition_service.entities.Nutrition;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
    @Override
    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Nutrition> findAll();

    @Override
    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    Optional<Nutrition> findById(Long id);

    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Nutrition> findByCategoryType(CategoryType type);

    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Nutrition> findByCategoryNameAndCategoryType(String categoryName, CategoryType type);

    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Nutrition> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
