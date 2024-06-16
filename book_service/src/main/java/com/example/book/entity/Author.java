package com.example.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
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
