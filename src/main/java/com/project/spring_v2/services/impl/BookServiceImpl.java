package com.project.spring_v2.services.impl;

import com.project.spring_v2.dto.BookDTO;
import com.project.spring_v2.entity.Book;
import com.project.spring_v2.services.BookService;
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
