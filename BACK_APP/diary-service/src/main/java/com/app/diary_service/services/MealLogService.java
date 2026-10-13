package com.app.diary_service.services;

import com.app.diary_service.dtos.requests.MealItemRequest;
import com.app.diary_service.dtos.requests.MealLogRequest;
import com.app.diary_service.dtos.responses.MealResponse;
import com.app.diary_service.entities.MealLog;
import com.app.diary_service.entities.MealLogsInfo;
import com.app.diary_service.exceptions.MealLogNotFoundException;
import com.app.diary_service.mappers.MealLogMapper;
import com.app.diary_service.repositories.MealLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealLogService {
    private final MealLogRepository mealLogRepository;
    private final DiaryService diaryService;
    private final MealLogMapper mealLogMapper;

    @Transactional(readOnly = true)
    public List<MealResponse> getMealLogs(Long userId, LocalDate date) {
        List<MealLog> mealLogs = date == null
                ? mealLogRepository.findAllByDiaryUserIdOrderByEatenAtDesc(userId)
                : mealLogRepository.findAllByDiaryUserIdAndEatenAtBetweenOrderByEatenAtAsc(
                        userId,
                        date.atStartOfDay(),
                        date.plusDays(1).atStartOfDay().minusNanos(1)
                );

        return mealLogMapper.toResponseList(mealLogs);
    }

    @Transactional
    public MealResponse createMealLog(Long userId, MealLogRequest request) {
        MealLog mealLog = MealLog.builder()
                .diary(diaryService.findDiary(userId))
                .mealType(request.mealType())
                .eatenAt(request.eatenAt())
                .build();

        for (MealItemRequest item : request.items()) {
            mealLog.getItems().add(MealLogsInfo.builder()
                    .mealLog(mealLog)
                    .nutritionId(item.nutritionId())
                    .weight(item.weight())
                    .build());
        }

        return mealLogMapper.toResponse(mealLogRepository.save(mealLog));
    }

    @Transactional
    public void deleteMealLog(Long userId, Long mealLogId) {
        MealLog mealLog = mealLogRepository.findByIdAndDiaryUserId(mealLogId, userId)
                .orElseThrow(MealLogNotFoundException::new);
        mealLogRepository.delete(mealLog);
    }
}
