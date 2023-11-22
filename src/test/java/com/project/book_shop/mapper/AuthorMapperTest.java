package com.project.book_shop.mapper;

import com.project.book_shop.dto.AuthorDTO;
import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import com.project.book_shop.entity.enums.Cover;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class AuthorMapperTest {

    @Autowired
    private AuthorMapper authorMapper;

    @Test
    public void testToAuthorDTO() {
        // Создаем тестовые данные
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setId(1L);
        book.setName("Test Book");
        book.setBrand("Test Brand");
        book.setCover(Cover.soft);
        books.add(book);

        Author author = new Author();
        author.setId(1L);
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setBooks(books);

        // Преобразуем Author в AuthorDTO
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // Проверяем, что маппер корректно отработал
        assertEquals(author.getId(), authorDTO.getId());
        assertEquals(author.getFirstName() + " " + author.getLastName(), authorDTO.getAuthorName());
        assertEquals(1, authorDTO.getBookIds().size());
        assertEquals(book.getId(), authorDTO.getBookIds().get(0));
    }

//    @Test
//    public void testToAuthor() {
//        // Создаем тестовые данные
//        AuthorDTO authorDTO = new AuthorDTO();
//        authorDTO.setId(1L);
//        authorDTO.setAuthorName("John Doe");
//        List<Long> bookIds = new ArrayList<>();
//        bookIds.add(1L);
//        authorDTO.setBookIds(bookIds);
//
//        // Преобразуем AuthorDTO в Author
//        Author author = authorMapper.toAuthor(authorDTO);
//
//        // Проверяем, что маппер корректно отработал
//        assertEquals(authorDTO.getId(), author.getId());
//
//        // Разбиваем authorName на firstName и lastName
//        String[] names = authorDTO.getAuthorName().split(" ");
//        assertEquals(names[0], author.getFirstName());
//        assertEquals(names[1], author.getLastName());
//
//        // Проверяем, что книги были созданы и добавлены к автору
//        assertEquals(1, author.getBooks().size());
//        assertEquals(bookIds.get(0), author.getBooks().get(0).getId());
//    }



    @Test
    public void testToAuthorDTOWithNull() {
        // Преобразуем null Author в AuthorDTO
        AuthorDTO authorDTO = authorMapper.toDto(null);

        // Проверяем, что маппер корректно отработал и вернул null
        assertNull(authorDTO);
    }

    @Test
    public void testToAuthorWithNull() {
        // Преобразуем null AuthorDTO в Author
        Author author = authorMapper.toAuthor(null);

        // Проверяем, что маппер корректно отработал и вернул null
        assertNull(author);
    }
}
