package com.example.book.repository;

import com.example.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Page<Book> findByAuthorId(UUID id, Pageable pageable);

    /**
     * Используется в методе удаления всех книг по id автора
     */
    List<Book> findByAuthorId(UUID id);
}
