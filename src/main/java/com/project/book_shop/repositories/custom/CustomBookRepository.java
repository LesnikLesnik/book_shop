package com.project.book_shop.repositories.custom;

import com.project.book_shop.entity.Book;
import com.project.book_shop.entity.models.BookFilter;

import java.util.List;

public interface CustomBookRepository {
    List<Book> findByFilter(BookFilter filter);
}
