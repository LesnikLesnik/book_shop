package com.example.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class AuthorResponseDto {

    private UUID id;

    private String name;

    private Date dateOfBirth;
}
