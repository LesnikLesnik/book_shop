package com.example.book.controller;

import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/")
    public UUID createBook(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.create(bookRequestDto);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable UUID id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/")
    public Page<BookResponseDto> getAllBooks(@PageableDefault(size = 15) Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @PutMapping("/{id}")
    public BookResponseDto update(@PathVariable UUID id, @RequestBody BookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        bookService.delete(id);
    }
}
