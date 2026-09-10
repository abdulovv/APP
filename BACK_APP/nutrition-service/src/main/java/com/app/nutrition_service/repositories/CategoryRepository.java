package com.app.nutrition_service.repositories;

import com.app.nutrition_service.entities.Category;
import com.app.nutrition_service.entities.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(CategoryType type);
}
