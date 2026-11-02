package com.app.product_service.repositories;

import com.app.product_service.entities.ProductCategory;
import com.app.product_service.entities.ProductCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findAllByOrderByNameAsc();

    List<ProductCategory> findAllByTypeOrderByNameAsc(ProductCategoryType type);
}
