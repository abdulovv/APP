package com.app.diary_service.services;

import com.app.diary_service.dtos.requests.MealLogItemRequest;
import com.app.diary_service.dtos.requests.MealLogRequest;
import com.app.diary_service.dtos.responses.MealLogResponse;
import com.app.diary_service.entities.MealLog;
import com.app.diary_service.entities.MealLogItem;
import com.app.diary_service.exceptions.MealLogNotFoundException;
import com.app.diary_service.mappers.MealLogMapper;
import com.app.diary_service.repositories.MealLogRepository;
import com.app.diary_service.utils.DateUtils;
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
    public List<MealLogResponse> getMealLogs(Long userId, LocalDate date) {
        List<MealLog> mealLogs = date == null
                ? mealLogRepository.findAllByDiaryUserIdOrderByEatenAtDesc(userId)
                : mealLogRepository.findAllByDiaryUserIdAndEatenAtBetweenOrderByEatenAtAsc(
                        userId,
                        DateUtils.startOfDay(date),
                        DateUtils.endOfDay(date)
                );

        return mealLogMapper.toResponseList(mealLogs);
    }

    @Transactional
    public MealLogResponse createMealLog(Long userId, MealLogRequest request) {
        MealLog mealLog = MealLog.builder()
                .diary(diaryService.findDiary(userId))
                .mealType(request.mealType())
                .eatenAt(request.eatenAt())
                .build();

        for (MealLogItemRequest item : request.items()) {
            mealLog.getItems().add(MealLogItem.builder()
                    .mealLog(mealLog)
                    .productId(item.productId())
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
