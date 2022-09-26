package com.edu.ulab.app.mapper.dto;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    Person userDtoToPerson(UserDto userDto);

    UserDto personToUserDto(Person personDto);
}
