package com.edu.ulab.app.mapper.request.update;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.web.request.update.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {
    UserDto userUpdateRequestToUserDto(UserUpdateRequest userUpdateRequest);

    UserUpdateRequest userDtoToUserUpdateRequest(UserDto userDto);
}
