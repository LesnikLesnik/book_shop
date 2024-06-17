package com.example.book.service;

import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    public UUID create(BookRequestDto bookRequestDto) {

    }

    public BookResponseDto getBookById(UUID id) {
    }

    public BookResponseDto getAllBooks(Pageable pageable) {
    }
}
