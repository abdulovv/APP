package com.app.diary_service.controllers;

import com.app.diary_service.dtos.common.BodyWeightLogDto;
import com.app.diary_service.dtos.common.DiaryDto;
import com.app.diary_service.dtos.requests.BodyWeightLogRequest;
import com.app.diary_service.dtos.requests.DiaryRequest;
import com.app.diary_service.services.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {
    private static final String USER_ID_HEADER = "X-User-Id";

    private final DiaryService diaryService;

    // ==================== DIARY ====================
    @GetMapping("/me")
    public ResponseEntity<DiaryDto> getDiary(@RequestHeader(value = USER_ID_HEADER) Long userId) {
        return ResponseEntity.ok(diaryService.getDiary(userId));
    }

    @PostMapping
    public ResponseEntity<DiaryDto> createDiary(
            @RequestHeader(value = USER_ID_HEADER) Long userId,
            @Valid @RequestBody DiaryRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryService.createDiary(userId, request));
    }

    @PutMapping("/me")
    public ResponseEntity<DiaryDto> updateDiary(
            @RequestHeader(value = USER_ID_HEADER) Long userId,
            @Valid @RequestBody DiaryRequest request
    ) {
        return ResponseEntity.ok(diaryService.updateDiary(userId, request));
    }

    // ==================== BODY WEIGHT ====================
    @GetMapping("/weights")
    public ResponseEntity<List<BodyWeightLogDto>> getBodyWeightLogs(@RequestHeader(value = USER_ID_HEADER) Long userId) {
        return ResponseEntity.ok(diaryService.getBodyWeightLogs(userId));
    }

    @PostMapping("/weights")
    public ResponseEntity<BodyWeightLogDto> addBodyWeightLog(
            @RequestHeader(value = USER_ID_HEADER) Long userId,
            @Valid @RequestBody BodyWeightLogRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryService.addBodyWeightLog(userId, request));
    }
}
