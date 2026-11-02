package com.app.product_service.repositories;

import com.app.product_service.entities.Product;
import com.app.product_service.entities.ProductCategoryType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Product> findAll();

    @Override
    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Product> findAllByCategoryType(ProductCategoryType type);

    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Product> findAllByCategoryTypeAndCategoryName(ProductCategoryType type, String categoryName);

    @EntityGraph(attributePaths = {"category", "nutrients", "nutrients.nutrient"})
    List<Product> findAllByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
