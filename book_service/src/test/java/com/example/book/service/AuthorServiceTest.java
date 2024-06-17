package com.example.book.service;

import com.example.book.dto.AuthorRequestDto;
import com.example.book.dto.AuthorResponseDto;
import com.example.book.entity.Author;
import com.example.book.exception.AuthorServiceException;
import com.example.book.mapper.AuthorMapper;
import com.example.book.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {


    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    private AuthorRequestDto authorRequestDto;
    private Author author;
    private AuthorResponseDto authorResponseDto;
    private UUID authorId;

    @BeforeEach
    public void setUp() {
        //Given for all tests
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date(1999-9 -9);

        authorRequestDto = new AuthorRequestDto(firstName, lastName, dateOfBirth);
        authorId = UUID.randomUUID();
        author = new Author(authorId, firstName, lastName, dateOfBirth);
        authorResponseDto = new AuthorResponseDto(authorId, firstName + " " + lastName, dateOfBirth);
    }

    @Test
    void testAddAuthor_NewAuthor_ShouldReturnId() {
        //When
        when(authorRepository.findFirstByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.empty());
        when(authorMapper.toAuthor(authorRequestDto)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);

        UUID result = authorService.addAuthor(authorRequestDto);

        //Then
        verify(authorRepository, times(1)).findFirstByFirstNameAndLastName("John", "Doe");
        verify(authorMapper, times(1)).toAuthor(authorRequestDto);
        verify(authorRepository, times(1)).save(author);
        assertEquals(author.getId(), result);
    }

    @Test
    void testAddAuthor_ExistingAuthorWithSameBirthDate_ShouldThrowException() {
        //Given
        Author existingAuthor = author;
        AuthorResponseDto existingAuthorResponse = new AuthorResponseDto(existingAuthor.getId(), existingAuthor.getFirstName() + " " + existingAuthor.getLastName(), existingAuthor.getDateOfBirth());

        //When
        when(authorRepository.findFirstByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(existingAuthor));
        when(authorMapper.toResponse(existingAuthor)).thenReturn(existingAuthorResponse);

        //Then
        assertThrows(AuthorServiceException.class, () -> authorService.addAuthor(authorRequestDto), "Такой автор уже есть в базе");
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void testGetAuthor_ExistingAuthor_ShouldReturnAuthorResponseDto() {
        //Given
        UUID authorId = UUID.randomUUID();
        Author existingAuthor = new Author(authorId, "John", "Doe", new Date(1999-9-9));
        AuthorResponseDto expectedResponse = new AuthorResponseDto(authorId, "John Doe", new Date(1999-9-9));

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorMapper.toResponse(existingAuthor)).thenReturn(expectedResponse);

        //When
        AuthorResponseDto result = authorService.getAuthor(authorId);

        //Then
        assertEquals(expectedResponse, result);
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, times(1)).toResponse(existingAuthor);
    }

    @Test
    void testGetAuthor_AuthorNotFound_ShouldThrowException() {
        //When
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        //Then
        assertThrows(AuthorServiceException.class, () -> authorService.getAuthor(authorId), "Автор с id " + authorId + " не найден");
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    void testGetAuthorByName_AuthorsFound_ShouldReturnPage() {
        //Given
        Pageable pageable = PageRequest.of(0, 10);
        Author author1 = author;
        Author author2 = new Author(UUID.randomUUID(), "John", "Doe", new Date(1999-9-10));
        Page<Author> authorsPage = new PageImpl<>(List.of(author1, author2));
        AuthorResponseDto author1Response = new AuthorResponseDto(author1.getId(), author1.getFirstName() + " " + author1.getLastName(), author1.getDateOfBirth());
        AuthorResponseDto author2Response = new AuthorResponseDto(author2.getId(), author2.getFirstName() + " " + author2.getLastName(), author2.getDateOfBirth());

        when(authorRepository.findByFirstNameAndLastName("John", "Doe", pageable)).thenReturn(authorsPage);
        when(authorMapper.toResponse(author1)).thenReturn(author1Response);
        when(authorMapper.toResponse(author2)).thenReturn(author2Response);

        //When
        Page<AuthorResponseDto> result = authorService.getAuthorByName(authorRequestDto, pageable);

        //Then
        assertEquals(2, result.getTotalElements());
        assertEquals(List.of(author1Response, author2Response), result.getContent());
        verify(authorRepository, times(1)).findByFirstNameAndLastName("John", "Doe", pageable);
        verify(authorMapper, times(1)).toResponse(author1);
        verify(authorMapper, times(1)).toResponse(author2);
    }
}
