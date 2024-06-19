package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookResponseDtoToRabbit {

    private UUID accountId;

    private String email;

    private UUID bookId;
}
