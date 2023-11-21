package com.project.book_shop.mapper;

import com.project.book_shop.dto.AuthorDTO;
import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface AuthorMapper {

    @Mapping(target = "books", ignore = true)
    Author toAuthor(AuthorDTO dto);

    @Mapping(target = "authorName", expression = "java(entity.getFirstName() + \" \" + entity.getLastName())")
    @Mapping(target = "bookIds", source = "entity.books", qualifiedByName = "extractBookIds")
    AuthorDTO toDto(Author entity);

    // метод для извлечения только идентификаторов книг
    @Named("extractBookIds")
    default List<Long> extractBookIds(List<Book> books) {
        return books.stream().map(Book::getId).toList();
    }
}

