package com.example.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AddBookRequestDto {

    private UUID accountId;

    private UUID bookId;
}