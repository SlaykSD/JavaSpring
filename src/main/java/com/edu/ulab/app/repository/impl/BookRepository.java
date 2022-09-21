package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.dto.BookDtoMapper;
import com.edu.ulab.app.repository.EntityRepository;
import com.edu.ulab.app.storage.impl.BookStorage;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookRepository implements EntityRepository<Long, BookDto> {
    private final BookStorage bookStorage;
    private final BookDtoMapper bookDtoMapper;

    public BookRepository(BookStorage bookStorage, BookDtoMapper bookDtoMapper) {
        this.bookStorage = bookStorage;
        this.bookDtoMapper = bookDtoMapper;
    }


    @Override
    public BookDto save(BookDto entity) {
        Book book = bookDtoMapper.bookDtoToBook(entity);
        if(book.getId()!= null){
            Book found = bookStorage.findById(book.getId());
            if(found != null){
                return bookDtoMapper.bookToBookDto(bookStorage.update(book));
            }
        }

        return bookDtoMapper.bookToBookDto(bookStorage.save(book));
    }

    @Override
    public Optional<BookDto> findById(Long primaryKey) {
       return  Optional.ofNullable(bookDtoMapper.bookToBookDto(bookStorage.findById(primaryKey)));
    }

    @Override
    public void delete(BookDto entity) {
        bookStorage.delete(bookDtoMapper.bookDtoToBook(entity).getId());
    }

    @Override
    public Iterable<BookDto> findAll() {
        return bookStorage.getAllEntity().stream().map(bookDtoMapper::bookToBookDto).toList();
    }

}
