package com.example.book.service;

import com.example.book.dto.AuthorRequestDto;
import com.example.book.dto.AuthorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorService authorService;
    public UUID addAuthor(AuthorRequestDto authorRequestDto) {

        return UUID.randomUUID();
    }

    public AuthorResponseDto getAuthor(UUID id) {

        return new AuthorResponseDto();
    }
}
