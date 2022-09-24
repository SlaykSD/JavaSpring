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
    private final  UserStorage userStorage;

    public BookStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Book save(Book entity) {
        entity.setId(AUTO_ID.getAndIncrement());
        books.put(AUTO_ID.get() - 1 , entity);
        User user = userStorage.findById(entity.getUserId());
        if(user!= null) {
            user.getBooksId().add(entity.getId());
            userStorage.update(user);
        }
        return entity;
    }

    @Override
    public Book findById(Long primaryKey) {
        return books.get(primaryKey);
    }

    @Override
    public void delete(Long primaryKey) {
        Book book = findById(primaryKey);
        books.remove(book.getId());
        User user = userStorage.findById(book.getUserId());
        if(user != null) {
            List<Long> updatedList = user.getBooksId();
            updatedList.remove(primaryKey);
            user.setBooksId(updatedList);
        }
    }

    @Override
    public Book update(Book entity) {
        books.remove(entity.getId());
        books.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<Book> getAllEntity() {
        return books.values();
    }
}
