package com.edu.ulab.app.repository.impl;


import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.dto.BookDtoMapper;
import com.edu.ulab.app.repository.EntityRepository;
import com.edu.ulab.app.storage.impl.BookStorage;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookRepository implements EntityRepository<Long, Book> {
    private final BookStorage bookStorage;
   // private final BookDtoMapper bookDtoMapper;

    public BookRepository(BookStorage bookStorage, BookDtoMapper bookDtoMapper) {
        this.bookStorage = bookStorage;

    }


    @Override
    public Book save(Book entity) {
        if(entity.getId()!= null){
            Book found = bookStorage.findById(entity.getId());
            if(found != null){
                return bookStorage.update(entity);
            }
        }

        return bookStorage.save(entity);
    }

    @Override
    public Optional<Book> findById(Long primaryKey) {
       return  Optional.ofNullable(bookStorage.findById(primaryKey));
    }

    @Override
    public void delete(Book entity) {
        bookStorage.delete(entity.getId());
    }

    @Override
    public Iterable<Book> findAll() {
        return bookStorage.getAllEntity();
    }

}
