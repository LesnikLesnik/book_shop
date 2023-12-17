package com.project.book_shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Long id;
    private String authorName;
    private List<Long> bookIds;
}