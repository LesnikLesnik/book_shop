package com.project.book_shop.repository.book;

import com.project.book_shop.entity.Book;
import com.project.book_shop.DTO.BookFilter;

import java.util.List;

public interface CustomBookRepository {
    List<Book> findByFilter(BookFilter filter);
}
