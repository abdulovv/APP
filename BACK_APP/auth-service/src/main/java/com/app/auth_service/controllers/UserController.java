package com.app.auth_service.controllers;

import com.app.auth_service.dtos.common.UserDTO;
import com.app.auth_service.exceptions.UserNotFoundException;
import com.app.auth_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/me")
    public UserDTO getCurrentUser() throws UserNotFoundException {
        return getUserById(1L);
    }

//    @PatchMapping("/name")
//    public ResponseEntity<UpdateResponse> updateName(@RequestBody UpdateNameRequest request) {
//        userService.updateName(request.getFirstname(), request.getLastname());
//        return ResponseEntity.ok(new UpdateResponse("Name updated."));
//    }
//
//    @PatchMapping("/email")
//    public ResponseEntity<UpdateResponse> updateEmail(UpdateEmailRequest request) {
//        userService.updateEmail(principal.getId(), request.getEmail());
//        return ResponseEntity.ok(new UpdateResponse("Email updated."));
//    }
//
//    @PatchMapping("/phone")
//    public ResponseEntity<UpdateResponse> updatePhone(@RequestBody UpdatePhoneRequest request) {
//        userService.updatePhone(principal.getId(), request.getPhoneNumber());
//        return ResponseEntity.ok(new UpdateResponse("Phone updated."));
//    }

    @PatchMapping("/password")
    public ResponseEntity updatePassword() {
        return ResponseEntity.ok("NTHG");
    }
}
