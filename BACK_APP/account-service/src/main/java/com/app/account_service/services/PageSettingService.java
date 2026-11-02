package com.app.account_service.services;

import com.app.account_service.dtos.responses.PageSettingResponse;
import com.app.account_service.dtos.requests.UpdatePageSettingRequest;
import com.app.account_service.entities.PageSetting;
import com.app.account_service.exceptions.PageSettingNotFoundException;
import com.app.account_service.mappers.PageSettingMapper;
import com.app.account_service.repositories.PageSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageSettingService {
    private final PageSettingRepository pageSettingRepository;
    private final PageSettingMapper pageSettingMapper;

    @Transactional(readOnly = true)
    public List<PageSettingResponse> getAllPageSettings() {
        return pageSettingMapper.toResponseList(pageSettingRepository.findAllByOrderByPageKeyAsc());
    }

    @Transactional
    public PageSettingResponse updatePageSetting(String pageKey, UpdatePageSettingRequest request) {
        PageSetting pageSetting = pageSettingRepository.findById(pageKey)
                .orElseThrow(PageSettingNotFoundException::new);

        pageSetting.setIsActive(request.isActive());
        pageSetting.setReason(request.reason());

        return pageSettingMapper.toResponse(pageSetting);
    }
}