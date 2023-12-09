package com.project.book_shop.controllers;

import com.project.book_shop.dto.BookDto;
import com.project.book_shop.dto.BookFilter;
import com.project.book_shop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDTO) {
        BookDto createdBook = bookService.saveBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        return book != null
                ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BookDto> getBookByName(@PathVariable String name) {
        BookDto book = bookService.getBookByName(name);
        return book != null
                ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDTO) {
        BookDto updatedBook = bookService.update(id, bookDTO);
        return updatedBook != null
                ? new ResponseEntity<>(updatedBook, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BookDto>> getBooksByFilter(@RequestBody BookFilter filter) {
        List<BookDto> filteredBooks = bookService.getBooksByFilter(filter);
        return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
    }
}
