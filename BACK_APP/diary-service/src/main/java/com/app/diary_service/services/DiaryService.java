package com.app.diary_service.services;

import com.app.diary_service.dtos.common.BodyWeightLogDto;
import com.app.diary_service.dtos.common.DiaryDto;
import com.app.diary_service.dtos.requests.BodyWeightLogRequest;
import com.app.diary_service.dtos.requests.DiaryRequest;
import com.app.diary_service.entities.BodyWeightLog;
import com.app.diary_service.entities.Diary;
import com.app.diary_service.exceptions.DiaryAlreadyExistsException;
import com.app.diary_service.exceptions.DiaryNotFoundException;
import com.app.diary_service.mappers.BodyWeightLogMapper;
import com.app.diary_service.mappers.DiaryMapper;
import com.app.diary_service.repositories.BodyWeightLogRepository;
import com.app.diary_service.repositories.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final BodyWeightLogRepository bodyWeightLogRepository;
    private final DiaryMapper diaryMapper;
    private final BodyWeightLogMapper bodyWeightLogMapper;

    // ==================== DIARY ====================
    @Transactional(readOnly = true)
    public DiaryDto getDiary(Long userId) {
        return diaryMapper.toDto(findDiary(userId));
    }

    @Transactional
    public DiaryDto createDiary(Long userId, DiaryRequest request) {
        if (diaryRepository.existsByUserId(userId)) {
            throw new DiaryAlreadyExistsException();
        }

        Diary diary = Diary.builder()
                .userId(userId)
                .height(request.height())
                .birthDate(request.birthDate())
                .build();

        return diaryMapper.toDto(diaryRepository.save(diary));
    }

    @Transactional
    public DiaryDto updateDiary(Long userId, DiaryRequest request) {
        Diary diary = findDiary(userId);
        diary.setHeight(request.height());
        diary.setBirthDate(request.birthDate());

        return diaryMapper.toDto(diary);
    }

    // ==================== BODY WEIGHT ====================
    @Transactional(readOnly = true)
    public List<BodyWeightLogDto> getBodyWeightLogs(Long userId) {
        return bodyWeightLogMapper.toDtoList(bodyWeightLogRepository.findAllByDiaryUserIdOrderByCreatedAtDesc(userId));
    }

    @Transactional
    public BodyWeightLogDto addBodyWeightLog(Long userId, BodyWeightLogRequest request) {
        BodyWeightLog log = BodyWeightLog.builder()
                .diary(findDiary(userId))
                .weight(request.weight())
                .build();

        return bodyWeightLogMapper.toDto(bodyWeightLogRepository.save(log));
    }

    public Diary findDiary(Long userId) {
        return diaryRepository.findByUserId(userId)
                .orElseThrow(DiaryNotFoundException::new);
    }
}
