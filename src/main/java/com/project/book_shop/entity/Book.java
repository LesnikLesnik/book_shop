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
    @Column(name = "book_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "cover")
    @Enumerated(EnumType.STRING)
    private Cover cover;

    @JoinColumn(name = "author")
    @ManyToOne
    private Author author;

    @Column(name = "count")
    private Integer count;
}
