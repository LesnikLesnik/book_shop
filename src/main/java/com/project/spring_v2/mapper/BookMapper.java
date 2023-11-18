package com.project.spring_v2.mapper;

import com.project.spring_v2.dto.BookDTO;
import com.project.spring_v2.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book toBook(BookDTO bookDto);
    BookDTO toBookDTO(Book book);
}
