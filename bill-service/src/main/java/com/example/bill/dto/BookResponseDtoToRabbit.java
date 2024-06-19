package com.example.bill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BookResponseDtoToRabbit {

    private UUID accountId;

    private String email;

    private UUID bookId;
}