package com.project.book_shop.services;

import com.project.book_shop.dto.AuthorDto;
import com.project.book_shop.dto.BookDto;
import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import com.project.book_shop.dto.BookFilter;
import com.project.book_shop.mapper.AuthorMapper;
import com.project.book_shop.mapper.BookMapper;
import com.project.book_shop.repositories.book.BookRepository;
import com.project.book_shop.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Transactional
    public BookDto saveBook(BookDto bookDto) {
        log.info("Starting to save book: {}", bookDto);

        if (bookDto.getId() != null) {
            throw new RuntimeException("Книга с id: " + bookDto.getId() + " уже существует" );
        }
        Book book = bookMapper.toBook(bookDto);
        book = bookRepository.save(book);

        log.info("Book saved successfully: {}", bookDto);
        return bookMapper.toBookDTO(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByFilter(BookFilter filter) {
        List<Book> filteredBooks = bookRepository.findByFilter(filter);
        return filteredBooks.stream().map(bookMapper::toBookDTO).toList();
    }
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        // Преобразуем список книг в список DTO с использованием маппера
        return books.stream()
                .map(bookMapper::toBookDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooksByAuthor(Long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);
        return books.stream().map(bookMapper::toBookDTO).toList();
    }


    @Transactional(readOnly = true)
    public BookDto getBookByName(String name) {
        Optional<Book> optionalBook = bookRepository.findByName(name);
        return optionalBook.map(bookMapper::toBookDTO)
                .orElseThrow(()-> new BookNotFoundException(name));
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        return optionalBook.map(bookMapper::toBookDTO)
                .orElseThrow(()-> new BookNotFoundException(id.toString()));
    }

    public BookDto update(BookDto bookDto){
        return bookRepository.findById(bookDto.getId())
                .map(existingBook -> bookMapper.updateBook(existingBook, bookDto))
                .map(bookRepository::save)
                .map(bookMapper::toBookDTO)
                .orElseThrow(() -> new BookNotFoundException(bookDto.getId().toString()));

    }


    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
