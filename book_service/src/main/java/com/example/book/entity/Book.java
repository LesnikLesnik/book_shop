package com.example.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "author_id")
    private UUID authorId;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "yearOfCreate")
    private Integer yearOfCreate;

    @Column(name = "pages")
    private Integer pages;
}
