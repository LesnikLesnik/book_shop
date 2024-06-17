package com.example.book.mapper;


import com.example.book.dto.AuthorRequestDto;
import com.example.book.dto.AuthorResponseDto;
import com.example.book.entity.Author;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorMapperTest {


    private final AuthorMapper mapper = Mappers.getMapper(AuthorMapper.class);

    @Test
    void testToAuthor() {

        //Given
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date(1991-10-19);
        AuthorRequestDto authorRequestDto = new AuthorRequestDto(firstName, lastName, dateOfBirth);

        //When
        Author author = mapper.toAuthor(authorRequestDto);

        //Then
        assertThat(author.getFirstName()).isEqualTo(firstName);
        assertThat(author.getLastName()).isEqualTo(lastName);
        assertThat(author.getDateOfBirth()).isEqualTo(dateOfBirth);
    }

    @Test
    void testToRequest() {

        //Given
        UUID id = UUID.randomUUID();
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date(1991-10-19);
        Author author = new Author(id, firstName, lastName, dateOfBirth);

        //When
        AuthorResponseDto authorResponseDto = mapper.toResponse(author);

        //Then
        assertThat(authorResponseDto.getId()).isEqualTo(id);
        assertThat(authorResponseDto.getName()).isEqualTo(firstName + " " + lastName);
        assertThat(authorResponseDto.getDateOfBirth()).isEqualTo(dateOfBirth);
    }
}
