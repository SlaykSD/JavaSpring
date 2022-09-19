package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.repository.impl.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;


    @Override
    public BookDto createBook(BookDto bookDto) {
        return bookRepository.save(bookDto);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if(bookDto.getId()!=null){
            if(getBookById(bookDto.getId())!=null){
                return bookRepository.save(bookDto);
            }
        }
        return null;
    }

    @Override
    public BookDto getBookById(Long id) {
        if(id!=null) {
            return  bookRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Book with id:%d not found"));
        }
        return null;
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.delete(getBookById(id));
    }

    @Override
    public List<BookDto> getBooksByUserId(Long id) {
        List<BookDto> allBooks = (List<BookDto>) bookRepository.findAll();

        return allBooks.stream().filter(book-> book.getUserId()!=null && book.getUserId().equals(id)).toList();
    }
}
