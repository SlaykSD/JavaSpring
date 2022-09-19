package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserStorage implements Storage<Long, User> {

    private final Map<Long,User> users= new HashMap<>();
    private static final AtomicLong AUTO_ID = new AtomicLong(1);


    @Override
    public User save(User entity) {
        entity.setId(AUTO_ID.getAndIncrement());
        users.put(AUTO_ID.get() - 1 , entity);
        return entity;
    }

    @Override
    public Optional<User> findById(Long primaryKey) {
        return Optional.ofNullable(users.get(primaryKey));
    }

    @Override
    public void delete(Long primaryKey) {
        Optional<User> optUser = findById(primaryKey);
        optUser.ifPresent(user -> users.remove(user.getId()));
    }

    @Override
    public User update(User entity) {
        User userToUpdate = findById(entity.getId()).get();

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
