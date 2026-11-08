package com.app.account_service.controllers;

import com.app.account_service.dtos.requests.GrantPremiumRequest;
import com.app.account_service.dtos.responses.AdminUserDetailsResponse;
import com.app.account_service.dtos.responses.AdminUserListItemResponse;
import com.app.account_service.dtos.responses.PremiumTransactionResponse;
import com.app.account_service.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserListItemResponse>> searchUsers(
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @RequestParam(name = "search", required = false) String search
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(adminService.searchUsers(search));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserDetailsResponse> getUserDetails(
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @PathVariable("id") Long id
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(adminService.getUserDetails(id));
    }

    @GetMapping("/users/{id}/premium-history")
    public ResponseEntity<List<PremiumTransactionResponse>> getPremiumHistory(
            @PathVariable Long id,
            @RequestHeader(GatewayHeaders.USER_ROLE) String role
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(adminService.getPremiumHistory(id));
    }

    @PostMapping("/premium/grant")
    public ResponseEntity<Void> grantPremium(
            @RequestHeader(GatewayHeaders.USER_ID) Long adminId,
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @Valid @RequestBody GrantPremiumRequest request
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        adminService.grantPremium(adminId, request);
        return ResponseEntity.ok().build();
    }
}
