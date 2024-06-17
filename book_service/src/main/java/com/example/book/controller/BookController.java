package com.example.book.controller;

import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

//    public UUID createBook(@RequestBody BookRequestDto bookRequestDto) {
//        return bookService.create(bookRequestDto);
//    }
//
//    public BookResponseDto getBookById(@PathVariable UUID id) {
//        return bookService.getBookById(id);
//    }
//
//    public BookResponseDto getAllBooks(@PageableDefault(size = 15) Pageable pageable) {
//        return bookService.getAllBooks(pageable);
//    }


}
