package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.mapper.dto.UserDtoMapper;
import com.edu.ulab.app.repository.EntityRepository;
import com.edu.ulab.app.storage.impl.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository implements EntityRepository<Long, User> {
    private final UserStorage userStorage;

    public UserRepository(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    @Override
    public User save(User entity) {
        if(entity.getId()!= null){
            User found = userStorage.findById(entity.getId());
            if(found != null){
                return  userStorage.update(entity);
            }
        }

        return userStorage.save(entity);
    }

    @Override
    public Optional<User> findById(Long primaryKey) {
        return  Optional.ofNullable(userStorage.findById(primaryKey));
    }

    @Override
    public void delete(User entity) {
        userStorage.delete(entity.getId());
    }

    @Override
    public Iterable<User> findAll() {
        return userStorage.getAllEntity();
    }


}
