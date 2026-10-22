package com.app.auth_service.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePageSettingRequest(
        @NotNull
        Boolean isActive,

        @Size(max = 255)
        String reason
) {
    public UpdatePageSettingRequest {
        if (reason != null && reason.isBlank()) {
            reason = null;
        } else if (reason != null) {
            reason = reason.trim();
        }
    }
}