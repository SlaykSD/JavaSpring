package com.edu.ulab.app.mapper.request;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.request.update.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    UserDto userUpdateRequestToUserDto(UserUpdateRequest userUpdateRequest);

    UserUpdateRequest userDtoToUserUpdateRequest(UserDto userDto);
}
