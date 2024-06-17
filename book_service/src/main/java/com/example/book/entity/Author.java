package com.example.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
public class Author {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    //firstName and lastName mapping and save like 'name'
    private String firstName;

    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

}
