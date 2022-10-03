package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.mapper.dto.BookDtoMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;



    @Override
    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = bookDtoMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookDtoMapper.bookToBookDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        bookRepository.findByIdForUpdate(bookDto.getId())
                .orElseThrow(() -> new BookNotFoundException(bookDto.getId()));
        Book book = bookDtoMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Update book: {}", savedBook);
        return bookDtoMapper.bookToBookDto(savedBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Got book by id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        log.info("A book was found {}", book);
        return bookDtoMapper.bookToBookDto(book);

    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        log.info("Got book id for delete: {}", id);
        BookDto book = getBookById(id);
        bookRepository.delete(bookDtoMapper.bookDtoToBook(book));
        log.info(String.format("The book with id=%d was successfully deleted", id));
    }

    @Override
    public List<BookDto> getBooksByUserId(Long id) {
        log.info("Get user books by userID: {}", id);
        List<Book> books = bookRepository.findBooksByUserId(id.intValue());
        log.info("A books list was found: {}", books);
        return books.stream().map(bookDtoMapper::bookToBookDto).toList() ;
    }

    @Override
    public List<Long> getBooksIdByUserId(Long id) {
        log.info("Get user booksID by userID: {}", id);
        List<Book> books = bookRepository.findBooksByUserId(id.intValue());
        log.info("A books list was found: {}", books);

        return books.stream().map(Book::getId).toList();
    }


}
