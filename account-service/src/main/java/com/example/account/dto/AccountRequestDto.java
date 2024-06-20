package com.example.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountRequestDto {

    private String login;

    private String email;

    private String password;

}
