package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserStorage implements Storage<Long, User> {

    private final Map<Long,User> users= new HashMap<>();
    private static final AtomicLong AUTO_ID = new AtomicLong(1);
//    private final BookStorage bookStorage;
//
//    public UserStorage(BookStorage bookStorage) {
//        this.bookStorage = bookStorage;
//    }

    @Override
    public User save(User entity) {
        entity.setId(AUTO_ID.getAndIncrement());
        entity.setBooksId(new ArrayList<>());
//        List<Long> booksId = bookStorage.getAllEntity().
//                stream().map(Book::getUserId).filter(userId-> userId.equals(entity.getId())).toList();
//        entity.setBooksId(booksId);
        users.put(AUTO_ID.get() - 1 , entity);
        return entity;
    }

    @Override
    public User findById(Long primaryKey) {
        return users.get(primaryKey);
    }

    @Override
    public void delete(Long primaryKey) {
        User user = findById(primaryKey);
        users.remove(user.getId());
    }

    @Override
    public User update(User entity) {
        User userToUpdate = findById(entity.getId());

        if (entity.getAge() == 0) {
            entity.setAge(userToUpdate.getAge());
        }
        if (entity.getTitle() == null) {
            entity.setTitle(userToUpdate.getTitle());
        }
        if (entity.getFullName() == null) {
            entity.setFullName(userToUpdate.getFullName());
        }
        users.remove(entity.getId());
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<User> getAllEntity() {
        return users.values();
    }
}
