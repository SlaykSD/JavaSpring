package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.dto.UserDtoMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.repository.impl.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return userDtoMapper.
                userToUserDto(userRepository.save(userDtoMapper.userDtoToUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if(userDto.getId() != null){
            return userDtoMapper.
                    userToUserDto(userRepository.save(userDtoMapper.userDtoToUser(getUserById(userDto.getId()))));
        }
        return null;
    }

    @Override
    public List<Long> getUserBookIds(Long id) {
        User user =userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("User with id:%d not found", id)));
        return user.getBooksId();
    }

    @Override
    public UserDto getUserById(Long id) {

        if(id!=null) {
            return  userDtoMapper.userToUserDto(userRepository.findById(id)
                    .orElseThrow(()->new NotFoundException(String.format("User with id:%d not found", id))));
        }
        throw new NotFoundException(String.format("User with id:%d not found", id));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.delete(userDtoMapper.userDtoToUser(getUserById(id)));
    }

}
