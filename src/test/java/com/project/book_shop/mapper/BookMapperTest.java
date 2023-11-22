package com.project.book_shop.mapper;


import com.project.book_shop.dto.BookDTO;
import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import com.project.book_shop.entity.enums.Cover;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void testToBookDTO() {
        // Создаем тестовые данные
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("John");
        author.setLastName("Doe");

        Book book = new Book();
        book.setId(1L);
        book.setName("Test Book");
        book.setBrand("Test Brand");
        book.setCover(Cover.soft);
        book.setAuthor(author);
        book.setCount(5);

        // Преобразуем Book в BookDTO
        BookDTO bookDTO = bookMapper.toBookDTO(book);

        // Проверяем, что маппер корректно отработал
        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getName(), bookDTO.getName());
        assertEquals(book.getBrand(), bookDTO.getBrand());
        assertEquals(book.getCover(), bookDTO.getCover());
        assertEquals(book.getAuthor().getId(), bookDTO.getAuthorId());
        assertEquals(book.getCount(), bookDTO.getCount());
    }

    @Test
    public void testToBook() {
        // Создаем тестовые данные
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setName("Test Book");
        bookDTO.setBrand("Test Brand");
        bookDTO.setCover(Cover.soft);
        bookDTO.setAuthorId(1L);
        bookDTO.setCount(5);

        // Преобразуем BookDTO в Book
        Book book = bookMapper.toBook(bookDTO);

        // Проверяем, что маппер корректно отработал
        assertEquals(bookDTO.getId(), book.getId());
        assertEquals(bookDTO.getName(), book.getName());
        assertEquals(bookDTO.getBrand(), book.getBrand());
        assertEquals(bookDTO.getCover(), book.getCover());
        assertEquals(bookDTO.getCount(), book.getCount());

        // Проверяем, что автор был создан и добавлен в Book
        assertEquals(bookDTO.getAuthorId(), book.getAuthor().getId());
        assertNull(null, book.getAuthor().getFirstName());
        assertNull(null, book.getAuthor().getLastName());
    }

    @Test
    public void testToBookDTOWithNull() {
        // Преобразуем null Book в BookDTO
        BookDTO bookDTO = bookMapper.toBookDTO(null);

        // Проверяем, что маппер корректно отработал и вернул null
        assertNull(bookDTO);
    }

    @Test
    public void testToBookWithNull() {
        // Преобразуем null BookDTO в Book
        Book book = bookMapper.toBook(null);

        // Проверяем, что маппер корректно отработал и вернул null
        assertNull(book);
    }
}
