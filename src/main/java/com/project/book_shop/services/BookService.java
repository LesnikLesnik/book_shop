package com.project.book_shop.services;

import com.project.book_shop.dto.BookDTO;


import java.util.List;


public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookByName (String name);
    BookDTO getBookById (Long id);
//    BookDTO update (Long id, BookDTO bookDTO);
    void deleteBookById(Long id);

}
