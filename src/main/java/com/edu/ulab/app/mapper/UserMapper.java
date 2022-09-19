package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.web.request.UserRequest;
import org.mapstruct.Mapper;
//TODO: может ли maper мапить 2 прослойки?
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

}
