package com.app.account_service.dtos.responses;


public record AdminUserListItemResponse(
        Long id,
        String firstname,
        String lastname,
        boolean premium
) {
}
