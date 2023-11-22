package com.project.book_shop.services;

import com.project.book_shop.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO saveAuthor(AuthorDTO authorDTO);

    AuthorDTO getAuthorById(Long id);

    List<AuthorDTO> getAllAuthors();
}
