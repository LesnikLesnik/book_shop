package com.project.book_shop.exception;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
