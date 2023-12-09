package com.project.book_shop.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message) {
        super("Книга не найдена: " + message);
    }

}
