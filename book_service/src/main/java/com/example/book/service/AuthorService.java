package com.example.book.service;

import com.example.book.dto.AuthorRequestDto;
import com.example.book.dto.AuthorResponseDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.exception.AuthorServiceException;
import com.example.book.mapper.AuthorMapper;
import com.example.book.mapper.BookMapper;
import com.example.book.repository.AuthorRepository;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorMapper authorMapper;

    private final BookMapper bookMapper;

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    public UUID addAuthor(AuthorRequestDto authorRequestDto) {
        log.info("start to add author");
        Optional<Author> authorForCheck = authorRepository.findFirstByFirstNameAndLastName(authorRequestDto.getFirstName(), authorRequestDto.getLastName());

        if (authorForCheck.isPresent()) {
            AuthorResponseDto authorResponseDto = authorMapper.toResponse(authorForCheck.get());
            log.info("Найден автор с идентичными именем и фамилией {}, выполняется проверка по дате рождения", authorResponseDto);

            if (authorResponseDto.getDateOfBirth().equals(authorRequestDto.getDateOfBirth())) {
                throw new AuthorServiceException("Такой автор уже есть в базе");
            }
        }
        Author author = authorMapper.toAuthor(authorRequestDto);
        author.setId(UUID.randomUUID());
        log.info("Add author {} successful", author);
        return authorRepository.save(author).getId();
    }

    public AuthorResponseDto getAuthor(UUID id) {
        return authorRepository.findById(id)
                .map(authorMapper::toResponse)
                .orElseThrow(() -> new AuthorServiceException("Автор с id " + id + " не найден"));
    }

    public Page<AuthorResponseDto> getAuthorByName(AuthorRequestDto authorRequestDto , Pageable pageable) {
        String firstName = authorRequestDto.getFirstName();
        String lastName = authorRequestDto.getLastName();
        log.info("start to find authors by name {} {}", firstName, lastName);

        Page<Author> authors = authorRepository.findByFirstNameAndLastName(firstName, lastName, pageable);

        if (authors.isEmpty()) {
            throw new AuthorServiceException("Автор " + firstName + " " + lastName + " не найден. Проверьте имя и фамилию");
        }

        log.info("Found {} authors with name {} {}", authors.getSize(), firstName, lastName);
        return authors.map(authorMapper::toResponse);
    }

    public AuthorResponseDto updateAuthor(UUID id, AuthorRequestDto authorRequestDto) {
        Author authorToUpdate = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorServiceException("Автор с id " + id + " не найден"));
        log.info("Author with id has been found: {}", authorToUpdate);

        authorMapper.updateAuthorFromDto(authorRequestDto, authorToUpdate);
        Author updatedAuthor = authorRepository.save(authorToUpdate);

        log.info("The author's update with id {} was completed successfully: {}", id, updatedAuthor);
        return authorMapper.toResponse(updatedAuthor);
    }

    public Page<BookResponseDto> getBooksByAuthorId(UUID authorId, Pageable pageable) {
        Page<Book> books = bookRepository.findByAuthorId(authorId, pageable);
        if (books.isEmpty()) {
            throw new AuthorServiceException("У автора с id " + authorId + " нет книг");
        }
        return books.map(bookMapper::toResponse);
    }

    public void deleteAuthor(UUID id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorServiceException("Автор с id " + id + " не найден");
        }
        log.info("Delete author with id {}", id);

        List<Book> booksByAuthor = bookRepository.findByAuthorId(id);
        bookRepository.deleteAll(booksByAuthor);

        authorRepository.deleteById(id);

        log.info("The author with id {} and all his books have been successfully deleted", id);
    }
}
