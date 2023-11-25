package com.project.book_shop.services.impl;

import com.project.book_shop.dto.AuthorDTO;
import com.project.book_shop.dto.BookDTO;
import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import com.project.book_shop.entity.models.BookFilter;
import com.project.book_shop.mapper.AuthorMapper;
import com.project.book_shop.mapper.BookMapper;
import com.project.book_shop.repositories.BookRepository;
import com.project.book_shop.repositories.custom.CustomBookRepository;
import com.project.book_shop.services.AuthorService;
import com.project.book_shop.services.BookService;
import com.project.book_shop.services.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    //TODO: удалить лишние методы

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    private final AuthorMapper authorMapper;
    private final CustomBookRepository customBookRepositoryImpl;

    //TODO: добавить логгирование для методов
    @Override
    @Transactional
    public BookDTO saveBook(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toBookDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByFilter(BookFilter filter) {
        List<Book> filteredBooks = customBookRepositoryImpl.findByFilter(filter);
        return filteredBooks.stream().map(bookMapper::toBookDTO).toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        // Преобразуем список книг в список DTO с использованием маппера
        return books.stream()
                .map(bookMapper::toBookDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooksByAuthor(Long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);
        return books.stream().map(bookMapper::toBookDTO).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookByName(String name) {
        Optional<Book> optionalBook = bookRepository.findByName(name);
        return optionalBook.map(bookMapper::toBookDTO)
                .orElseThrow(()-> new BookNotFoundException("Книга " + name + " не найдена!"));
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        return optionalBook.map(bookMapper::toBookDTO)
                .orElseThrow(()-> new BookNotFoundException("Книга с " + id + " не найдена!"));
    }

    //TODO: сделать рефактор метода
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
