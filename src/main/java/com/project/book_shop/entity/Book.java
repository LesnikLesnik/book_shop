package com.project.book_shop.entity;

import com.project.book_shop.entity.enums.Cover;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "cover")
    @Enumerated(EnumType.STRING)
    private Cover cover;

    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;

    @Column(name = "count")
    private Integer count;
}