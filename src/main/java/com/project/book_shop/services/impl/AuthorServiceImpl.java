package com.project.book_shop.services.impl;

import com.project.book_shop.dto.AuthorDTO;
import com.project.book_shop.entity.Author;
import com.project.book_shop.mapper.AuthorMapper;
import com.project.book_shop.repositories.AuthorRepository;
import com.project.book_shop.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public AuthorDTO saveAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toAuthor(authorDTO);
        author = authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorById(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.map(authorMapper::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toDto).collect(Collectors.toList());
    }
}
