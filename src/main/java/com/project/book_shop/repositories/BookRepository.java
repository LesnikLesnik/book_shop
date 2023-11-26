package com.project.book_shop.repositories;

import com.project.book_shop.entity.Book;
import com.project.book_shop.repositories.custom.CustomBookRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, CustomBookRepository {
    Optional<Book> findByName(String name);
    Optional<Book> findById(Long id);

    List<Book> findByAuthorId(Long authorId);
}
