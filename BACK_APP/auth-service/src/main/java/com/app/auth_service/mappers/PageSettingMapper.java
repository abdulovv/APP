package com.app.auth_service.mappers;

import com.app.auth_service.dtos.common.PageSettingDTO;
import com.app.auth_service.entities.PageSetting;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageSettingMapper {
    PageSettingDTO toDto(PageSetting pageSetting);

    List<PageSettingDTO> toDtoList(List<PageSetting> pageSettings);
}