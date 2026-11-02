package com.app.product_service.services;

import com.app.product_service.dtos.ProductDto;
import com.app.product_service.entities.Product;
import com.app.product_service.entities.ProductCategoryType;
import com.app.product_service.exceptions.ProductNotFoundException;
import com.app.product_service.mappers.ProductMapper;
import com.app.product_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryService productCategoryService;
    private final NutrientService nutrientService;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByType(ProductCategoryType type) {
        return productMapper.toDtoList(productRepository.findAllByCategoryType(type));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByTypeAndCategoryName(ProductCategoryType type, String categoryName) {
        return productMapper.toDtoList(productRepository.findAllByCategoryTypeAndCategoryName(type, categoryName));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> searchProductsByName(String query) {
        return productMapper.toDtoList(productRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(query.trim()));
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        return productMapper.toDto(findProduct(id));
    }

    @Transactional
    public ProductDto createProduct(ProductDto request, ProductCategoryType expectedType) {
        Product product = productMapper.toEntity(request);
        product.setCategory(productCategoryService.getCategoryOfType(request.getCategory(), expectedType));
        nutrientService.addNutrientsToProduct(product, request.getNutrients());

        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto request) {
        Product product = findProduct(id);
        productMapper.updateEntity(request, product);

        if (request.getCategory() != null) {
            product.setCategory(productCategoryService.getCategory(request.getCategory()));
        }
        if (request.getNutrients() != null && !request.getNutrients().isEmpty()) {
            nutrientService.replaceProductNutrients(product, request.getNutrients());
        }

        return productMapper.toDto(productRepository.saveAndFlush(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void deactivateProduct(Long id) {
        findProduct(id).setIsActive(false);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
