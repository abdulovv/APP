package com.app.account_service.controllers;

import com.app.account_service.dtos.requests.GrantPremiumRequest;
import com.app.account_service.services.PremiumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/premium")
public class PremiumController {
    private final PremiumService premiumService;

    @PostMapping("/grant")
    public ResponseEntity<Void> grantPremium(
            @RequestHeader(GatewayHeaders.USER_ID) Long adminId,
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @Valid @RequestBody GrantPremiumRequest request
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        premiumService.grantPremium(adminId, request);
        return ResponseEntity.ok().build();
    }
}
