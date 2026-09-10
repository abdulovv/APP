package com.app.nutrition_service.services;

import com.app.nutrition_service.repositories.NutrientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NutrientService {
    private final NutrientRepository nutrientRepository;

}
