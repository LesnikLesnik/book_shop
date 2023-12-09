package com.project.book_shop.services;

import com.project.book_shop.dto.AuthorDto;
import com.project.book_shop.entity.Author;
import com.project.book_shop.mapper.AuthorMapper;
import com.project.book_shop.repositories.AuthorRepository;
import com.project.book_shop.exception.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toAuthor(authorDto);
        author = authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.map(authorMapper::toDto).orElseThrow(()-> new AuthorNotFoundException("Такой автора не найден"));
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toDto).toList();
    }
}
