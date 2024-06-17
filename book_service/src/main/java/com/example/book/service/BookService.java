package com.example.book.service;

import com.example.book.dto.AuthorResponseDto;
import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.entity.Book;
import com.example.book.exception.BookServiceException;
import com.example.book.mapper.BookMapper;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    public UUID create(BookRequestDto bookRequestDto) {
        log.info("Start to create book");
        Book book = bookMapper.toBook(bookRequestDto);
        book.setId(UUID.randomUUID());
        log.info("Book to save {}", book);
        return bookRepository.save(book).getId();
    }

    public BookResponseDto getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookServiceException("Книга с id" + id + " не найдена"));
        log.info("Find book successful {}", book);
        BookResponseDto bookResponseDto = bookMapper.toResponse(book);
        AuthorResponseDto authorName = authorService.getAuthor(book.getAuthorId());
        log.info("Find book`s author {}", authorName);
        bookResponseDto.setAuthorName(authorName.getName());
        return bookResponseDto;
    }

    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(bookMapper::toResponse);
    }

    public BookResponseDto update(UUID id, BookRequestDto bookRequestDto) {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new BookServiceException("Книга с id" + id + " не найдена"));
        log.info("Book to update {}", bookToUpdate);
        bookMapper.updateBookFromDto(bookRequestDto, bookToUpdate);
        bookRepository.save(bookToUpdate);
        log.info("Book after update {}", bookToUpdate);
        return bookMapper.toResponse(bookToUpdate);
    }

    public void delete(UUID id) {
        bookRepository.deleteById(id);
        log.info("Book with id {} has been deleted", id);
    }
}
