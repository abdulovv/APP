package com.app.account_service.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageSettingResponse {
    private String pageKey;
    private Boolean isActive;
    private String reason;
}