package com.example.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestDto {

    private String login;

    private String email;

    private String password;

}
