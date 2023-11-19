package com.project.book_shop.services.impl;

import com.project.book_shop.dto.BookDTO;
import com.project.book_shop.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return null;
    }

    @Override
    public BookDTO getBookByName(String name) {
        return null;
    }

    @Override
    public BookDTO getBookById(Long id) {
        return null;
    }

    @Override
    public BookDTO update(Long id, BookDTO bookDTO) {
        return null;
    }

    @Override
    public void deleteBookById(Long id, BookDTO bookDTO) {

    }
}
