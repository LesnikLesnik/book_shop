package com.example.book.repository;

import com.example.book.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Page<Author> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);
    Optional<Author> findFirstByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth);
}
