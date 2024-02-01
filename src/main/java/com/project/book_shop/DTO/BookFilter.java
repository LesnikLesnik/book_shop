package com.project.book_shop.DTO;

import com.project.book_shop.enums.Cover;
import lombok.Data;

@Data
public class BookFilter {
    private String name;
    private String brand;
    private Cover cover;
    private String authorFirstName;
    private String authorLastName;
    private Integer count;
}

