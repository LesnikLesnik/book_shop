package com.project.book_shop.services.impl;

import com.project.book_shop.dto.AuthorDTO;
import com.project.book_shop.dto.BookDTO;
import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import com.project.book_shop.mapper.AuthorMapper;
import com.project.book_shop.mapper.BookMapper;
import com.project.book_shop.repositories.BookRepository;
import com.project.book_shop.services.AuthorService;
import com.project.book_shop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    //TODO: удалить лишние методы

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    //TODO: добавить логгирование для методов
    @Override
    @Transactional
    public BookDTO saveBook(BookDTO bookDTO) {
        // Используем маппер для преобразования BookDTO в Book
        Book book = bookMapper.toBook(bookDTO);

        // Сохраняем книгу в репозиторий
        book = bookRepository.save(book);

        // Возвращаем сохраненную книгу, преобразованную обратно в BookDTO
        return bookMapper.toBookDTO(book);
    }


    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        // Получаем список всех книг из репозитория
        List<Book> books = bookRepository.findAll();

        // Преобразуем список книг в список DTO с использованием маппера
        return books.stream()
                .map(bookMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooksByAuthor(Long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);
        return books.stream().map(bookMapper::toBookDTO).collect(Collectors.toList());
    }

    //TODO: создать исключение в случае ненахода книги
    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookByName(String name) {
        // Используем репозиторий для поиска книги по названию
        Optional<Book> optionalBook = bookRepository.findByName(name);

        // Если книга найдена, возвращаем её в виде DTO, иначе возвращаем null
        return optionalBook.map(bookMapper::toBookDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        // Используем репозиторий для поиска книги по идентификатору
        Optional<Book> optionalBook = bookRepository.findById(id);

        // Если книга найдена, возвращаем её в виде DTO, иначе возвращаем null
        return optionalBook.map(bookMapper::toBookDTO).orElse(null);
    }

    @Override
    @Transactional
    public BookDTO update(Long id, BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Проверяем, существует ли автор с заданным идентификатором
            Long authorId = bookDTO.getAuthorId();
            AuthorDTO authorDTO = authorService.getAuthorById(authorId);
            Author author = authorMapper.toAuthor(authorDTO);


            if (author != null) {
                // Обновляем только те поля, которые необходимо изменить
                existingBook.setName(bookDTO.getName());
                existingBook.setBrand(bookDTO.getBrand());
                existingBook.setCover(bookDTO.getCover());
                existingBook.setAuthor(author);
                existingBook.setCount(bookDTO.getCount());

                existingBook = bookRepository.save(existingBook);

                return bookMapper.toBookDTO(existingBook);
            }
        }

        // Если книга не найдена или автор не найден, возвращаем null
        return null;
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
