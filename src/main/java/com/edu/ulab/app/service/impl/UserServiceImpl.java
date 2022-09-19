package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.repository.impl.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return userRepository.save(userDto);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if(userDto.getId() != null){
            return userRepository.save(getUserById(userDto.getId()));
        }
        return null;
    }


    @Override
    public UserDto getUserById(Long id) {

        if(id!=null) {
            userRepository.findById(id)
                    .orElseThrow(()->new NotFoundException(String.format("User with id:%d not found", id)));
        }
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.delete(getUserById(id));
    }

}
