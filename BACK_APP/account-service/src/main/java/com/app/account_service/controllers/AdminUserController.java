package com.app.account_service.controllers;

import com.app.account_service.dtos.responses.AdminUserDetailsResponse;
import com.app.account_service.dtos.responses.AdminUserListItemResponse;
import com.app.account_service.services.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<List<AdminUserListItemResponse>> searchUsers(
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @RequestParam(name = "search", required = false) String search
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(adminUserService.searchUsers(search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUserDetailsResponse> getUserDetails(
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @PathVariable("id") Long id
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(adminUserService.getUserDetails(id));
    }
}
