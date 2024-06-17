package com.example.book.controller;

import com.example.book.dto.AuthorRequestDto;
import com.example.book.dto.AuthorResponseDto;
import com.example.book.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/")
    public UUID addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        return authorService.addAuthor(authorRequestDto);
    }

    @GetMapping("/{id}")
    public AuthorResponseDto getAuthor(@PathVariable UUID id){
        return authorService.getAuthor(id);
    }

    @GetMapping
    public AuthorResponseDto getAuthorByName()
}
