package com.project.book_shop.services.impl;

import com.project.book_shop.dto.BookDTO;
import com.project.book_shop.entity.Book;
import com.project.book_shop.mapper.BookMapper;
import com.project.book_shop.repositories.BookRepository;
import com.project.book_shop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    //TODO: расставить аннотации Transactional etc...

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    //TODO: добавить логгирование для методов
    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        // Используем маппер для преобразования BookDTO в Book
        Book book = bookMapper.toBook(bookDTO);

        // Сохраняем книгу в репозиторий
        book = bookRepository.save(book);

        // Возвращаем сохраненную книгу, преобразованную обратно в BookDTO
        return bookMapper.toBookDTO(book);
    }


    @Override
    public List<BookDTO> getAllBooks() {
        // Получаем список всех книг из репозитория
        List<Book> books = bookRepository.findAll();

        // Преобразуем список книг в список DTO с использованием маппера
        return books.stream()
                .map(bookMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    //TODO: создать исключение в случае ненахода книги
    @Override
    public BookDTO getBookByName(String name) {
        // Используем репозиторий для поиска книги по названию
        Optional<Book> optionalBook = bookRepository.findByName(name);

        // Если книга найдена, возвращаем её в виде DTO, иначе возвращаем null
        return optionalBook.map(bookMapper::toBookDTO).orElse(null);
    }

    @Override
    public BookDTO getBookById(Long id) {
        // Используем репозиторий для поиска книги по идентификатору
        Optional<Book> optionalBook = bookRepository.findById(id);

        // Если книга найдена, возвращаем её в виде DTO, иначе возвращаем null
        return optionalBook.map(bookMapper::toBookDTO).orElse(null);
    }

//    @Override
//    public BookDTO update(Long id, BookDTO bookDTO) {
//        // Проверяем, существует ли книга с заданным идентификатором
//        return Optional.ofNullable(bookRepository.findById(id))
//                .map(optionalBook -> {
//                    // Если книга существует, обновляем её данные
//                    Book existingBook = optionalBook.get();
//                    existingBook.setName(bookDTO.getName());
//                    existingBook.setBrand(bookDTO.getBrand());
//                    existingBook.setCover(bookDTO.getCover());
//                    existingBook.setAuthor(bookDTO.getAuthor());
//                    existingBook.setCount(bookDTO.getCount());
//
//                    // Сохраняем обновленную книгу в репозиторий
//                    existingBook = bookRepository.save(existingBook);
//
//                    // Возвращаем обновленную книгу в виде DTO
//                    return bookMapper.toBookDTO(existingBook);
//                })
//                .orElse(null);
//    }
//    @Override
//    public BookDTO update(Long id, BookDTO bookDTO) {
//        // Проверяем, существует ли книга с заданным идентификатором
//        Optional<Book> optionalBook = bookRepository.findById(id);
//
//        if (optionalBook.isPresent()) {
//            // Если книга существует, обновляем её данные
//            Book existingBook = optionalBook.get();
//            existingBook.setName(bookDTO.getName());
//            existingBook.setBrand(bookDTO.getBrand());
//            existingBook.setCover(bookDTO.getCover());
//            existingBook.setAuthor(bookDTO.getAuthor());
//            existingBook.setCount(bookDTO.getCount());
//
//            // Сохраняем обновленную книгу в репозиторий
//            existingBook = bookRepository.save(existingBook);
//
//            // Возвращаем обновленную книгу в виде DTO
//            return bookMapper.toBookDTO(existingBook);
//        } else {
//            return null;
//        }
//    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
