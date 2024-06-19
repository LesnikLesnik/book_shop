package com.example.book.controller;

import com.example.book.controller.annotations.DefaultApiResponses;
import com.example.book.dto.BookForSaleResponseDto;
import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@DefaultApiResponses
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Tag(description = "Book controller", name = "Контроллер книг")
public class BookController {

    private final BookService bookService;

    @PostMapping("/")
    @Operation(summary = "Создание книги")
    public UUID createBook(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.create(bookRequestDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение книги по id")
    public BookResponseDto getBookById(@PathVariable UUID id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/")
    @Operation(summary = "Получение всех книг")
    public Page<BookResponseDto> getAllBooks(@PageableDefault(size = 15) Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление книги", description = "Не обновляется поле id")
    public BookResponseDto update(@PathVariable UUID id, @RequestBody BookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }

    @GetMapping("/sell/{id}")
    @Operation(summary = "Получение книги для продажи",
            description = "Получение id и cost, id добавляется в соответствущее поле аккаунта, стоимость списывается со счета")
    public BookForSaleResponseDto getBookForSale(@PathVariable UUID id) {
        return bookService.getBookForSale(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление книги")
    public void delete(@PathVariable UUID id) {
        bookService.delete(id);
    }
}
