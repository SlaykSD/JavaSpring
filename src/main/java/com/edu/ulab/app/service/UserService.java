package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    List<Long> getUserBookIds(Long id);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);
}
