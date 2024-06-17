package com.example.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthorRequestDto {

    private String firstName;

    private String lastName;

    private Date dateOfBirth;
}
