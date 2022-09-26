package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.dto.UserDtoMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        Person user = userDtoMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userDtoMapper.personToUserDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        userRepository.findByIdForUpdate(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        Person user = userDtoMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person updatePerson = userRepository.save(user);
        log.info("Update user: {}", updatePerson);
        return userDtoMapper.personToUserDto(updatePerson);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);
        Person person = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("A user was found: {}", person);
        return userDtoMapper.personToUserDto(person);

    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        log.info("Got user id for delete: {}", id);
        Person person = userDtoMapper.userDtoToPerson(getUserById(id));
        userRepository.delete(person);
        log.info(String.format("The user with id%d was successfully deleted", id));
    }

}
