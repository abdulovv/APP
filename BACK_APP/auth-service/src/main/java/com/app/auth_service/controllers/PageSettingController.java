package com.app.auth_service.controllers;

import com.app.auth_service.dtos.common.PageSettingDTO;
import com.app.auth_service.dtos.requests.UpdatePageSettingRequest;
import com.app.auth_service.services.PageSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pages")
public class PageSettingController {
    private final PageSettingService pageSettingService;

    @GetMapping
    public ResponseEntity<List<PageSettingDTO>> getAll(){
        return ResponseEntity.ok().body(pageSettingService.getAll());
    }

    @PutMapping("/{pageKey}")
    public ResponseEntity update(
            @Valid @RequestBody UpdatePageSettingRequest request,
            @PathVariable("pageKey") String pageKey,
            @RequestHeader("X-User-Role") String role
    ){
        if (!isAdmin(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        pageSettingService.update(pageKey, request);
        return ResponseEntity.ok().build();
    }

    private static boolean isAdmin(String role){
        return "ADMIN".equals(role);
    }
}
