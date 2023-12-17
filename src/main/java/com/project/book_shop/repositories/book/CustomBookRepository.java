package com.project.book_shop.repositories.book;

import com.project.book_shop.entity.Book;
import com.project.book_shop.dto.BookFilter;

import java.util.List;

public interface CustomBookRepository {
    List<Book> findByFilter(BookFilter filter);
}
