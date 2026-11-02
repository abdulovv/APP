package com.app.account_service.mappers;

import com.app.account_service.dtos.responses.PageSettingResponse;
import com.app.account_service.entities.PageSetting;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageSettingMapper {
    PageSettingResponse toResponse(PageSetting pageSetting);

    List<PageSettingResponse> toResponseList(List<PageSetting> pageSettings);
}