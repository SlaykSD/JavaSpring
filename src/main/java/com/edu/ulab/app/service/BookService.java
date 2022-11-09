package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookService {
    BookDto createBook(BookDto userDto);

    BookDto updateBook(BookDto userDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    List<BookDto> getBooksByUserId(Long id);

    List<Long> getBooksIdByUserId(Long id);
}
