package com.app.diary_service.controllers;

import com.app.diary_service.dtos.responses.BodyWeightLogResponse;
import com.app.diary_service.dtos.responses.DiaryResponse;
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
    private final DiaryService diaryService;

    @GetMapping("/me")
    public ResponseEntity<DiaryResponse> getDiary(@RequestHeader(GatewayHeaders.USER_ID) Long userId) {
        return ResponseEntity.ok(diaryService.getDiary(userId));
    }

    @PostMapping
    public ResponseEntity<DiaryResponse> createDiary(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody DiaryRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryService.createDiary(userId, request));
    }

    @PutMapping("/me")
    public ResponseEntity<DiaryResponse> updateDiary(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody DiaryRequest request
    ) {
        return ResponseEntity.ok(diaryService.updateDiary(userId, request));
    }

    @GetMapping("/weights")
    public ResponseEntity<List<BodyWeightLogResponse>> getBodyWeightLogs(@RequestHeader(GatewayHeaders.USER_ID) Long userId) {
        return ResponseEntity.ok(diaryService.getBodyWeightLogs(userId));
    }

    @PostMapping("/weights")
    public ResponseEntity<BodyWeightLogResponse> addBodyWeightLog(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody BodyWeightLogRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryService.addBodyWeightLog(userId, request));
    }
}
