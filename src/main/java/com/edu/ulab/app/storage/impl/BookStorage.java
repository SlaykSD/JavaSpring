package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class BookStorage implements Storage<Long, Book> {
    private final Map<Long, Book> books= new HashMap<>();
    private static final AtomicLong AUTO_ID = new AtomicLong(1);

    @Override
    public Book save(Book entity) {
        entity.setId(AUTO_ID.getAndIncrement());
        books.put(AUTO_ID.get() - 1 , entity);
        return entity;
    }

    @Override
    public Optional<Book> findById(Long primaryKey) {
        return Optional.ofNullable(books.get(primaryKey));
    }

    @Override
    public void delete(Long primaryKey) {
        Optional<Book> optBook = findById(primaryKey);
        optBook.ifPresent(book -> books.remove(book.getId()));
    }

    @Override
    public Book update(Book entity) {
        if(entity.getId() ==  null || findById(entity.getId()).isEmpty()){
            throw  new NotFoundException("User not found with id: "+ entity.getId());
        }
        Book bookToUpdate = findById(entity.getId()).get();

        if (entity.getAuthor() == null) {
            entity.setAuthor(bookToUpdate.getAuthor());
        }
        if (entity.getTitle() == null) {
            entity.setTitle(bookToUpdate.getTitle());
        }
        if (entity.getPageCount() == 0) {
            entity.setPageCount(bookToUpdate.getPageCount());
        }
        if (entity.getUserId() == null) {
            entity.setUserId(bookToUpdate.getUserId());
        }
        books.remove(entity.getId());
        books.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<Book> getAllEntity() {
        return books.values();
    }
}
