package com.app.account_service.controllers;

import com.app.account_service.dtos.requests.UpdatePageSettingRequest;
import com.app.account_service.dtos.responses.PageSettingResponse;
import com.app.account_service.services.PageSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pages")
public class PageSettingController {
    private final PageSettingService pageSettingService;

    @GetMapping
    public ResponseEntity<List<PageSettingResponse>> getAllPageSettings() {
        return ResponseEntity.ok(pageSettingService.getAllPageSettings());
    }

    @PutMapping("/{pageKey}")
    public ResponseEntity<PageSettingResponse> updatePageSetting(
            @PathVariable("pageKey") String pageKey,
            @RequestHeader(GatewayHeaders.USER_ROLE) String role,
            @Valid @RequestBody UpdatePageSettingRequest request
    ) {
        if (!GatewayHeaders.isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(pageSettingService.updatePageSetting(pageKey, request));
    }
}
