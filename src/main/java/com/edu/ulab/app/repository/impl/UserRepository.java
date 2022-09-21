package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.mapper.dto.UserDtoMapper;
import com.edu.ulab.app.repository.EntityRepository;
import com.edu.ulab.app.storage.impl.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository implements EntityRepository<Long, UserDto> {
    private final UserStorage userStorage;
    private final UserDtoMapper userDtoMapper;

    public UserRepository(UserStorage userStorage, UserDtoMapper userDtoMapper) {
        this.userStorage = userStorage;
        this.userDtoMapper = userDtoMapper;
    }


    @Override
    public UserDto save(UserDto entity) {
        User user = userDtoMapper.userDtoToUser(entity);
        if(user.getId()!= null){
            User found = userStorage.findById(user.getId());
            if(found != null){
                return  userDtoMapper.userToUserDto(userStorage.update(user));
            }
        }

        return userDtoMapper.userToUserDto(userStorage.save(user));
    }

    @Override
    public Optional<UserDto> findById(Long primaryKey) {
        return  Optional.ofNullable(userDtoMapper.userToUserDto(userStorage.findById(primaryKey)));
    }

    @Override
    public void delete(UserDto entity) {
        userStorage.delete(userDtoMapper.userDtoToUser(entity).getId());
    }

    @Override
    public Iterable<UserDto> findAll() {
        return userStorage.getAllEntity().stream().map(userDtoMapper::userToUserDto).toList();
    }


}
