package com.project.spring_v2.services;

import com.project.spring_v2.dto.BookDTO;


import java.util.List;


public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookByName (String name);
    BookDTO getBookById (Long id);
    BookDTO update (Long id, BookDTO bookDTO);
    void deleteBookById(Long id, BookDTO bookDTO);

}
