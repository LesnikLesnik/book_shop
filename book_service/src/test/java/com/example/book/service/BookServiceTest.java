package com.example.book.service;

import com.example.book.dto.AuthorResponseDto;
import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.exception.BookServiceException;
import com.example.book.mapper.AuthorMapper;
import com.example.book.mapper.BookMapper;
import com.example.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCreate() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthorId(UUID.randomUUID());
        Book book = new Book();
        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setId(bookRequestDto.getAuthorId());
        Author author = new Author();

        when(bookMapper.toBook(bookRequestDto)).thenReturn(book);
        when(authorService.getAuthor(bookRequestDto.getAuthorId())).thenReturn(authorResponseDto);
        when(authorMapper.responseToAuthor(authorResponseDto)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        UUID id = bookService.create(bookRequestDto);

        assertNotNull(id);
        verify(bookMapper, times(1)).toBook(bookRequestDto);
        verify(authorService, times(1)).getAuthor(bookRequestDto.getAuthorId());
        verify(authorMapper, times(1)).responseToAuthor(authorResponseDto);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookMapper, bookRepository, authorService, authorMapper);
    }

    @Test
    void testGetBookById() {
        UUID id = UUID.randomUUID();
        Book book = new Book();
        Author author = new Author();
        book.setAuthor(author);
        BookResponseDto bookResponseDto = new BookResponseDto();
        AuthorResponseDto authorResponseDto = new AuthorResponseDto();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(bookResponseDto);
        when(authorMapper.toResponse(author)).thenReturn(authorResponseDto);

        BookResponseDto result = bookService.getBookById(id);

        assertEquals(bookResponseDto, result);
        assertEquals(authorResponseDto, result.getAuthorResponseDto());
        verify(bookRepository, times(1)).findById(id);
        verify(bookMapper, times(1)).toResponse(book);
        verify(authorMapper, times(1)).toResponse(author);
        verifyNoMoreInteractions(bookRepository, bookMapper, authorMapper);
    }

    @Test
    void testGetBookByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookServiceException.class, () -> bookService.getBookById(id));
        verify(bookRepository, times(1)).findById(id);
        verifyNoMoreInteractions(bookMapper, authorService, bookRepository, authorMapper);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        List<BookResponseDto> bookResponseDtos = Arrays.asList(new BookResponseDto(), new BookResponseDto());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toResponse(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return bookResponseDtos.get(books.indexOf(book));
        });

        Page<BookResponseDto> result = bookService.getAllBooks(pageable);

        assertEquals(bookResponseDtos, result.getContent());
        assertEquals(books.size(), result.getTotalElements());
        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(books.size())).toResponse(any(Book.class));
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void testUpdate() {
        UUID id = UUID.randomUUID();
        Book book = new Book();
        BookRequestDto bookRequestDto = new BookRequestDto();
        BookResponseDto bookResponseDto = new BookResponseDto();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(bookResponseDto);

        BookResponseDto result = bookService.update(id, bookRequestDto);

        assertEquals(bookResponseDto, result);
        verify(bookRepository, times(1)).findById(id);
        verify(bookMapper, times(1)).updateBookFromDto(bookRequestDto, book);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toResponse(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();
        BookRequestDto bookRequestDto = new BookRequestDto();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookServiceException.class, () -> bookService.update(id, bookRequestDto));
        verify(bookRepository, times(1)).findById(id);
        verifyNoMoreInteractions(bookMapper, bookRepository);
    }

    @Test
    void testDelete() {
        UUID id = UUID.randomUUID();

        bookService.delete(id);

        verify(bookRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(bookRepository);
    }
}

