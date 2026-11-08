package com.app.diary_service.services;

import com.app.diary_service.dtos.responses.BodyWeightLogResponse;
import com.app.diary_service.dtos.responses.DiaryResponse;
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

    @Transactional(readOnly = true)
    public DiaryResponse getDiary(Long userId) {
        return diaryMapper.toResponse(findDiary(userId));
    }

    @Transactional
    public DiaryResponse createDiary(Long userId, DiaryRequest request) {
        if (diaryRepository.existsByUserId(userId)) {
            throw new DiaryAlreadyExistsException();
        }

        Diary diary = Diary.builder()
                .userId(userId)
                .height(request.height())
                .birthDate(request.birthDate())
                .build();

        return diaryMapper.toResponse(diaryRepository.save(diary));
    }

    @Transactional
    public DiaryResponse updateDiary(Long userId, DiaryRequest request) {
        Diary diary = findDiary(userId);
        diary.setHeight(request.height());
        diary.setBirthDate(request.birthDate());

        return diaryMapper.toResponse(diary);
    }

    @Transactional(readOnly = true)
    public List<BodyWeightLogResponse> getBodyWeightLogs(Long userId) {
        return bodyWeightLogMapper.toResponseList(bodyWeightLogRepository.findAllByDiaryUserIdOrderByCreatedAtDesc(userId));
    }

    @Transactional
    public BodyWeightLogResponse addBodyWeightLog(Long userId, BodyWeightLogRequest request) {
        BodyWeightLog log = BodyWeightLog.builder()
                .diary(findDiary(userId))
                .weight(request.weight())
                .build();

        return bodyWeightLogMapper.toResponse(bodyWeightLogRepository.save(log));
    }

    public Diary findDiary(Long userId) {
        return diaryRepository.findByUserId(userId)
                .orElseThrow(DiaryNotFoundException::new);
    }
}
