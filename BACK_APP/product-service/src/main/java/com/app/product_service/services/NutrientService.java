package com.app.product_service.services;

import com.app.product_service.dtos.NutrientResponse;
import com.app.product_service.dtos.ProductNutrientDto;
import com.app.product_service.entities.Nutrient;
import com.app.product_service.entities.Product;
import com.app.product_service.entities.ProductNutrient;
import com.app.product_service.exceptions.NutrientNotFoundException;
import com.app.product_service.mappers.NutrientMapper;
import com.app.product_service.repositories.NutrientRepository;
import com.app.product_service.utils.NutrientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NutrientService {
    private final NutrientRepository nutrientRepository;
    private final NutrientMapper nutrientMapper;

    @Transactional(readOnly = true)
    public List<NutrientResponse> getAllNutrients() {
        return nutrientMapper.toResponseList(nutrientRepository.findAll());
    }

    public void addNutrientsToProduct(Product product, List<ProductNutrientDto> nutrients) {
        NutrientUtils.validate(nutrients);

        for (ProductNutrientDto nutrientValue : nutrients) {
            product.getNutrients().add(ProductNutrient.builder()
                    .product(product)
                    .nutrient(findNutrient(nutrientValue.getNutrientId()))
                    .value(nutrientValue.getValue())
                    .build());
        }
    }

    public void replaceProductNutrients(Product product, List<ProductNutrientDto> nutrients) {
        NutrientUtils.validate(nutrients);

        Map<Long, BigDecimal> newValuesByNutrientId = NutrientUtils.valuesByNutrientId(nutrients);

        Iterator<ProductNutrient> iterator = product.getNutrients().iterator();
        while (iterator.hasNext()) {
            ProductNutrient productNutrient = iterator.next();
            BigDecimal newValue = newValuesByNutrientId.remove(productNutrient.getNutrient().getId());
            if (newValue == null) {
                iterator.remove();
            } else {
                productNutrient.setValue(newValue);
            }
        }

        newValuesByNutrientId.forEach((nutrientId, value) ->
                product.getNutrients().add(ProductNutrient.builder()
                        .product(product)
                        .nutrient(findNutrient(nutrientId))
                        .value(value)
                        .build()));
    }

    private Nutrient findNutrient(Long nutrientId) {
        return nutrientRepository.findById(nutrientId).orElseThrow(NutrientNotFoundException::new);
    }
}
