package com.example.book.exception;

public class AuthorServiceException extends RuntimeException {
    public AuthorServiceException(String message) {
        super(message);
    }
}
