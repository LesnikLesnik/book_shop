package com.project.book_shop.entity;

import com.project.book_shop.entity.enums.Cover;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private Cover cover;

    private String author;

    private Integer count;
}
