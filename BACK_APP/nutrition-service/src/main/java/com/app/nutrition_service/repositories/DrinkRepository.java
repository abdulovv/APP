package com.app.nutrition_service.repositories;

import com.app.nutrition_service.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findByCategoryName(String categoryName);
}
