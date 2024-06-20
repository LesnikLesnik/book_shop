package com.example.account.controller;

import com.example.account.dto.AccountRequestDto;
import com.example.account.entity.AuthResponse;
import com.example.account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AccountRequestDto requestDto) {

        return ResponseEntity.ok(authService.register(requestDto));
    }
}
