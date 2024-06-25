package com.example.account.controller;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AuthResponseDto;
import com.example.account.dto.LoginRequestDto;
import com.example.account.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(description = "Auth controller", name = "Контроллер аутентификации")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    @Operation(summary = "Регистрация и создание аккаунта")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AccountRequestDto requestDto) {
        return ResponseEntity.ok(authService.register(requestDto));
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Вход в существующий аккаунт")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
