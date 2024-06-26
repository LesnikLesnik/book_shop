package com.example.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookRequestDto {

    private String title;

    private UUID authorId;

    private Integer cost;

    private Integer yearOfCreate;

    private Integer pages;
}
