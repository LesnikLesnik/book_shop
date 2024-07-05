package com.example.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "year_of_create")
    private Integer yearOfCreate;

    @Column(name = "pages")
    private Integer pages;
}
