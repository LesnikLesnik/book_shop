package com.project.spring_v2.dto;

import com.project.spring_v2.entity.enums.Cover;
import lombok.Data;

@Data
public class BookDTO {

    private Long id;

    private String name;

    private String brand;

    private Cover cover;

    private String author;

    private Integer count;
}
