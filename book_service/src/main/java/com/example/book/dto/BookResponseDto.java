package com.example.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookResponseDto {

    private UUID id;

    private String title;

    private AuthorResponseDto authorResponseDto;

    private Integer cost;

    private Integer yearOfCreate;

    private Integer pages;
}
