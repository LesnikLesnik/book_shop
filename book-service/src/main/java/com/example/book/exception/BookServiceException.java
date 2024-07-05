package com.example.book.exception;

public class BookServiceException extends RuntimeException{
    public BookServiceException(String message) {
        super(message);
    }
}
