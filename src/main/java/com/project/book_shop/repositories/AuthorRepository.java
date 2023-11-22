package com.project.book_shop.repositories;

import com.project.book_shop.entity.Author;
import com.project.book_shop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
