package com.project.book_shop.mapper;

import com.project.book_shop.dto.BookDTO;
import com.project.book_shop.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookMapper {
    @Mapping(target = "authorId", source = "author.id") // Маппим authorId на id автора
    BookDTO toBookDTO(Book book);

    @Mapping(target = "author.id", source = "authorId") // Маппим id автора на authorId
    Book toBook(BookDTO bookDto);
}
