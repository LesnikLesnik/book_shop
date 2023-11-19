package com.project.book_shop.mapper;

import com.project.book_shop.dto.BookDTO;
import com.project.book_shop.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book toBook(BookDTO bookDto);
    BookDTO toBookDTO(Book book);
}
