package com.example.book.mapper;

import com.example.book.dto.BookRequestDto;
import com.example.book.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public class BookMapper {

    Book toBook(BookRequestDto bookRequestDto);
}
