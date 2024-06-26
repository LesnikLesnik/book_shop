package com.example.book.controller;

import com.example.book.controller.annotations.DefaultApiResponses;
import com.example.book.dto.AuthorRequestDto;
import com.example.book.dto.AuthorResponseDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.service.AuthorService;
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
@RequestMapping("/api/authors")
@Tag(description = "Author controller", name = "Контроллер авторов")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/")
    @Operation(summary = "Добавление автора")
    public UUID addAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.addAuthor(authorRequestDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение автора по id")
    public AuthorResponseDto getAuthor(@PathVariable UUID id) {
        return authorService.getAuthor(id);
    }

    @GetMapping
    @Operation(summary = "Получение всех авторов по 15 штук на странице")
    public Page<AuthorResponseDto> getAllAuthors(@PageableDefault(size = 15) Pageable pageable) {
        return authorService.getAllAuthors(pageable);
    }

    @GetMapping("/author")
    @Operation(summary = "Получение автора по имени", description = "Name маппится из имени и фамилии")
    public Page<AuthorResponseDto> getAuthorByName(@RequestBody AuthorRequestDto authorRequestDto, Pageable pageable) {
        return authorService.getAuthorByName(authorRequestDto, pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление автора", description = "Не обновляется поле id")
    public AuthorResponseDto updateAuthor(@PathVariable UUID id, @RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.updateAuthor(id, authorRequestDto);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Получение всех книг по id автора", description = "Дефолтное кол-во книг на странице - 10")
    public Page<BookResponseDto> getBooksByAuthorId(@PathVariable UUID id, Pageable pageable) {
        return authorService.getBooksByAuthorId(id, pageable);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление автора")
    public void deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
    }

}
