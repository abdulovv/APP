package com.app.account_service.controllers;

import com.app.account_service.dtos.requests.AddCardRequest;
import com.app.account_service.dtos.responses.PaymentCardResponse;
import com.app.account_service.services.PaymentCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/cards")
public class PaymentCardController {
    private final PaymentCardService paymentCardService;

    @GetMapping
    public ResponseEntity<List<PaymentCardResponse>> getCards(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId
    ) {
        return ResponseEntity.ok(paymentCardService.getCards(userId));
    }

    @PostMapping
    public ResponseEntity<PaymentCardResponse> addCard(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody AddCardRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentCardService.addCard(userId, request));
    }

    @PatchMapping("/{id}/default")
    public ResponseEntity<Void> makeDefault(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @PathVariable("id") Long id
    ) {
        paymentCardService.makeDefault(userId, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCard(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @PathVariable("id") Long id
    ) {
        paymentCardService.removeCard(userId, id);
        return ResponseEntity.noContent().build();
    }
}
