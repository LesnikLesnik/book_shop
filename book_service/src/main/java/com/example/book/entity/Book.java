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
    @Column(name = "")
    @EqualsAndHashCode.Include
    private UUID id;

    private String title;

    private UUID authorId;

    private Integer cost;
}
