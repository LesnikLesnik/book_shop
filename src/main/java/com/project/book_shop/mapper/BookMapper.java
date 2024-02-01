package com.project.book_shop.mapper;

import com.project.book_shop.DTO.BookDto;
import com.project.book_shop.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface BookMapper {
    @Mapping(target = "authorId", source = "author.id") // Маппим authorId на id автора
    BookDto toBookDTO(Book book);

    @Mapping(target = "author.id", source = "authorId") // Маппим id автора на authorId
    Book toBook(BookDto bookDto);

    Book updateBook(@MappingTarget Book existingBook, BookDto bookDto);
}
