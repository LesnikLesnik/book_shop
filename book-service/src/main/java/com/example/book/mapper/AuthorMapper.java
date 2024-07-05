package com.example.book.mapper;

import com.example.book.dto.AuthorRequestDto;
import com.example.book.dto.AuthorResponseDto;
import com.example.book.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    Author toAuthor(AuthorRequestDto authorRequestDto);

    @Mapping(target = "name", expression = "java(author.getFirstName() + \" \" + author.getLastName())")
    AuthorResponseDto toResponse(Author author);

    @Mapping(target = "id", ignore = true)
    void updateAuthorFromDto(AuthorRequestDto authorRequestDto, @MappingTarget Author author);

    /**
     * получение автора для связывания с сущностью Book
     */
    Author responseToAuthor(AuthorResponseDto authorResponseDto);
}
