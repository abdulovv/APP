package com.app.product_service.controllers;

import com.app.product_service.dtos.NutrientResponse;
import com.app.product_service.dtos.ProductCategoryDto;
import com.app.product_service.dtos.ProductDto;
import com.app.product_service.entities.ProductCategoryType;
import com.app.product_service.services.NutrientService;
import com.app.product_service.services.ProductCategoryService;
import com.app.product_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final NutrientService nutrientService;

    // ==================== DRINKS ====================
    @GetMapping("/drinks")
    public ResponseEntity<List<ProductDto>> getAllDrinks() {
        return ResponseEntity.ok(productService.getProductsByType(ProductCategoryType.DRINK));
    }

    @GetMapping("/drinks/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getDrinksByCategoryName(@PathVariable("categoryName") String categoryName) {
        return ResponseEntity.ok(productService.getProductsByTypeAndCategoryName(ProductCategoryType.DRINK, categoryName));
    }

    @PostMapping("/drinks")
    public ResponseEntity<ProductDto> createDrink(
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @RequestBody ProductDto request
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request, ProductCategoryType.DRINK));
    }

    // ==================== FOODS ====================
    @GetMapping("/foods")
    public ResponseEntity<List<ProductDto>> getAllFoods() {
        return ResponseEntity.ok(productService.getProductsByType(ProductCategoryType.FOOD));
    }

    @GetMapping("/foods/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getFoodsByCategoryName(@PathVariable("categoryName") String categoryName) {
        return ResponseEntity.ok(productService.getProductsByTypeAndCategoryName(ProductCategoryType.FOOD, categoryName));
    }

    @PostMapping("/foods")
    public ResponseEntity<ProductDto> createFood(
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @RequestBody ProductDto request
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request, ProductCategoryType.FOOD));
    }

    // ==================== DICTIONARIES ====================
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryDto>> getCategories(
            @RequestParam(name = "type", required = false) ProductCategoryType type
    ) {
        List<ProductCategoryDto> categories = type == null
                ? productCategoryService.getAllCategories()
                : productCategoryService.getCategoriesByType(type);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/nutrients")
    public ResponseEntity<List<NutrientResponse>> getNutrients() {
        return ResponseEntity.ok(nutrientService.getAllNutrients());
    }

    // ==================== ALL PRODUCTS ====================
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam("query") String query) {
        return ResponseEntity.ok(productService.searchProductsByName(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @RequestBody ProductDto request
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateProduct(
            @PathVariable("id") Long id,
            @RequestHeader(GatewayHeaders.USER_ROLE) String role
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") Long id,
            @RequestHeader(GatewayHeaders.USER_ROLE) String role
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
