package com.example.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private AccountResponseDto account;

    public AuthResponseDto(String accessToken, String refreshToken, AccountResponseDto account) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.account = account;
    }
}
