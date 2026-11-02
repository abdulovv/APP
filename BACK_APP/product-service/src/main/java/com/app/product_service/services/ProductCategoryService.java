package com.app.product_service.services;

import com.app.product_service.dtos.ProductCategoryDto;
import com.app.product_service.entities.ProductCategory;
import com.app.product_service.entities.ProductCategoryType;
import com.app.product_service.exceptions.ProductCategoryNotFoundException;
import com.app.product_service.exceptions.ProductCategoryTypeMismatchException;
import com.app.product_service.mappers.ProductCategoryMapper;
import com.app.product_service.repositories.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    @Transactional(readOnly = true)
    public List<ProductCategoryDto> getAllCategories() {
        return productCategoryMapper.toDtoList(productCategoryRepository.findAllByOrderByNameAsc());
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryDto> getCategoriesByType(ProductCategoryType type) {
        return productCategoryMapper.toDtoList(productCategoryRepository.findAllByTypeOrderByNameAsc(type));
    }

    public ProductCategory getCategory(ProductCategoryDto categoryReference) {
        if (categoryReference == null || categoryReference.getId() == null) {
            throw new ProductCategoryNotFoundException();
        }
        return productCategoryRepository.findById(categoryReference.getId())
                .orElseThrow(ProductCategoryNotFoundException::new);
    }

    public ProductCategory getCategoryOfType(ProductCategoryDto categoryReference, ProductCategoryType expectedType) {
        ProductCategory category = getCategory(categoryReference);
        if (category.getType() != expectedType) {
            throw new ProductCategoryTypeMismatchException(
                    "Category " + category.getName() + " is not of type " + expectedType);
        }
        return category;
    }
}
