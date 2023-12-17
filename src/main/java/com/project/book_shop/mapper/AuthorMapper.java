package com.project.book_shop.mapper;

import com.project.book_shop.dto.AuthorDto;
import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public abstract class AuthorMapper {

    @Mapping(target = "books", ignore = true)
    public abstract Author toAuthor(AuthorDto dto);

    @Mapping(target = "authorName", expression = "java(entity.getFirstName() + \" \" + entity.getLastName())")
    @Mapping(target = "bookIds", source = "entity.books", qualifiedByName = "extractBookIds")
    public abstract AuthorDto toDto(Author entity);

    // метод для извлечения только идентификаторов книг
    @Named("extractBookIds")
    protected List<Long> extractBookIds(List<Book> books) {
        return books.stream().map(Book::getId).toList();
    }
}

