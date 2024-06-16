package com.example.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;
import org.mapstruct.Mapping;

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

    private Integer yearOfCreate;
}
