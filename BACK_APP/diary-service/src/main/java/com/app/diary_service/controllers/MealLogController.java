package com.app.diary_service.controllers;

import com.app.diary_service.dtos.requests.MealLogRequest;
import com.app.diary_service.dtos.responses.MealLogResponse;
import com.app.diary_service.services.MealLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary/meal-logs")
public class MealLogController {

    private final MealLogService mealLogService;

    @GetMapping
    public ResponseEntity<List<MealLogResponse>> getMealLogs(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(mealLogService.getMealLogs(userId, date));
    }

    @PostMapping
    public ResponseEntity<MealLogResponse> createMealLog(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody MealLogRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealLogService.createMealLog(userId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealLog(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @PathVariable("id") Long id
    ) {
        mealLogService.deleteMealLog(userId, id);
        return ResponseEntity.noContent().build();
    }
}
