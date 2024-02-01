package com.project.book_shop.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Long id;
    private String authorName;
    private List<Long> bookIds;
}