package com.project.spring_v2.services;

import com.project.spring_v2.entity.Book;
import org.springframework.stereotype.Service;


public interface BookService {

    Book addBook(Book book);
}
