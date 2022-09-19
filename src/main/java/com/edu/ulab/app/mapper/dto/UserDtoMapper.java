package com.edu.ulab.app.mapper.dto;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {
    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User userDto);
}
