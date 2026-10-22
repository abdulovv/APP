package com.app.auth_service.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageSettingDTO {
    private String pageKey;
    private Boolean isActive;
    private String reason;
}