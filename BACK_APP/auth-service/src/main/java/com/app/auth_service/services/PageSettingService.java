package com.app.auth_service.services;

import com.app.auth_service.dtos.common.PageSettingDTO;
import com.app.auth_service.dtos.requests.UpdatePageSettingRequest;
import com.app.auth_service.entities.PageSetting;
import com.app.auth_service.exceptions.PageNotFoundException;
import com.app.auth_service.mappers.PageSettingMapper;
import com.app.auth_service.repositories.PageSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageSettingService {
    private final PageSettingRepository pageSettingsRepository;
    private final PageSettingMapper pageSettingMapper;

    @Transactional(readOnly = true)
    public List<PageSettingDTO> getAll() {
        return pageSettingMapper.toDtoList(pageSettingsRepository.findAllByOrderByPageKeyAsc());
    }

    @Transactional
    public PageSettingDTO update(String pageKey, UpdatePageSettingRequest request) {
        PageSetting page = pageSettingsRepository.findById(pageKey)
                .orElseThrow(PageNotFoundException::new);

        page.setIsActive(request.isActive());
        page.setReason(request.reason());

        // save() не нужен: сущность managed, Hibernate сам запишет изменения при коммите
        return pageSettingMapper.toDto(page);
    }
}