package com.example.book.mapper;

import com.example.book.dto.BookRequestDto;
import com.example.book.dto.BookResponseDto;
import com.example.book.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toBook(BookRequestDto bookRequestDto);

    @Mapping(target = "authorResponseDto", ignore = true)
    BookResponseDto toResponse(Book book);

    @Mapping(target = "id", ignore = true)
    void updateBookFromDto(BookRequestDto bookRequestDto, @MappingTarget Book book);
}
