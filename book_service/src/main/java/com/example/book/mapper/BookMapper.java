package com.example.book.mapper;

import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toBook(BookRequestDto bookRequestDto);

    BookResponseDto toResponse(Book book);
}
