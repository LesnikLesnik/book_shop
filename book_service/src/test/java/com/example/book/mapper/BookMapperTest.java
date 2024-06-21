package com.example.book.mapper;

import com.example.book.dto.BookRequestDto;
import com.example.book.entity.Author;
import com.example.book.entity.Book;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookMapperTest {

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Test
    void testToBook() {
        //Given
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Test Book");
        bookRequestDto.setAuthorId(UUID.randomUUID());
        bookRequestDto.setCost(20);
        bookRequestDto.setYearOfCreate(2023);
        bookRequestDto.setPages(300);

        //When
        Book book = bookMapper.toBook(bookRequestDto);

        //Then
        assertEquals(bookRequestDto.getTitle(), book.getTitle());
        assertNull(book.getAuthor()); // Author is not set during mapping, it should be set later
        assertEquals(bookRequestDto.getCost(), book.getCost());
        assertEquals(bookRequestDto.getYearOfCreate(), book.getYearOfCreate());
        assertEquals(bookRequestDto.getPages(), book.getPages());
        assertNull(book.getId());
    }


    @Test
    void testUpdateBookFromDto() {
        //Given
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Old Title");
        book.setCost(10);
        book.setYearOfCreate(2022);
        book.setPages(200);

        Author author = new Author();
        author.setId(UUID.randomUUID());
        book.setAuthor(author);

        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("New Title");
        bookRequestDto.setAuthorId(UUID.randomUUID());
        bookRequestDto.setCost(20);
        bookRequestDto.setYearOfCreate(2023);
        bookRequestDto.setPages(300);

        //When
        bookMapper.updateBookFromDto(bookRequestDto, book);

        //Then
        assertEquals(bookRequestDto.getTitle(), book.getTitle());
        assertEquals(bookRequestDto.getCost(), book.getCost());
        assertEquals(bookRequestDto.getYearOfCreate(), book.getYearOfCreate());
        assertEquals(bookRequestDto.getPages(), book.getPages());
    }
}
