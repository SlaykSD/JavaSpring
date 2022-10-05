package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.dto.UserDtoMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link UserServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing user functionality.")
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserDtoMapper userMapper;

    @Test
    @DisplayName("Создание пользователя. Должно пройти успешно.")
    void savePersonTest() {
        //given

        UserDto userDto = new UserDto();
        userDto.setAge(11);
        userDto.setFullName("test name");
        userDto.setTitle("test title");

        Person person  = new Person();
        person.setFullName("test name");
        person.setAge(11);
        person.setTitle("test title");

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");


        //when

        when(userMapper.userDtoToPerson(userDto)).thenReturn(person);
        when(userRepository.save(person)).thenReturn(savedPerson);
        when(userMapper.personToUserDto(savedPerson)).thenReturn(result);


        //then

        UserDto userDtoResult = userService.createUser(userDto);
        assertEquals(1L, userDtoResult.getId());
    }

    // update
    @Test
    @DisplayName("Изменить пользователя. Должно пройти успешно.")
    void updatePersonTest() {
        //given
        Person person  = new Person();
        person.setId(1L);
        person.setFullName("test name");
        person.setAge(11);
        person.setTitle("test title");

        UserDto userUpdateDto = new UserDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setAge(31);
        userUpdateDto.setFullName("Ed Sheeran");
        userUpdateDto.setTitle("I see fire");

        Person updatePerson  = new Person();
        updatePerson.setId(1L);
        updatePerson.setFullName("Ed Sheeran");
        updatePerson.setAge(54);
        updatePerson.setTitle("I see fire int the mountain");

        UserDto resultUpdate = new UserDto();
        resultUpdate.setId(1L);
        resultUpdate.setFullName("Ed Sheeran");
        resultUpdate.setAge(54);
        resultUpdate.setTitle("I see fire int the mountain");

        //when

        when(userRepository.findByIdForUpdate(updatePerson.getId())).thenReturn(Optional.of(person));
        when(userMapper.userDtoToPerson(userUpdateDto)).thenReturn(updatePerson);
        when(userRepository.save(updatePerson)).thenReturn(updatePerson);
        when(userMapper.personToUserDto(updatePerson)).thenReturn(resultUpdate);


        //then
        UserDto userDtoResult = userService.updateUser(userUpdateDto);
        assertEquals(1L, userDtoResult.getId());
        assertEquals(54, userDtoResult.getAge());
        assertEquals("Ed Sheeran", userDtoResult.getFullName());
        assertEquals("I see fire int the mountain", userDtoResult.getTitle());
    }
    // get
    @Test
    @DisplayName("Выдать пользователя. Должно пройти успешно.")
    void getPersonTest() {
        //given
        Long userId = 1L;

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");


        //when

        when(userRepository.findById(userId)).thenReturn(Optional.of(savedPerson));
        when(userMapper.personToUserDto(savedPerson)).thenReturn(result);


        //then

        UserDto userDtoResult = userService.getUserById(userId);
        assertEquals(1L, userDtoResult.getId());
        assertEquals(11, userDtoResult.getAge());
        assertEquals("test name", userDtoResult.getFullName());
    }
    // delete
    @Test
    @DisplayName("Удалить пользователя. Должно пройти успешно.")
    void deletePersonTest() {
        //given

        Long personId = 1L;

        Person person  = new Person();
        person.setId(personId);
        person.setFullName("test name");
        person.setAge(11);
        person.setTitle("test title");

        UserDto userDto = new UserDto();
        userDto.setAge(11);
        userDto.setFullName("test name");
        userDto.setTitle("test title");


        //when
        when(userRepository.findById(personId)).thenReturn(Optional.of(person));
        when(userMapper.personToUserDto(person)).thenReturn(userDto);
        doNothing().when(userRepository).deleteById(personId);

        //then
        userService.deleteUserById(personId);
    }

    // * failed

    @Test
    @DisplayName("Ошибка при выдаче пользователя. Должно пройти успешно.")
    void failedGetPersonTest() {
        //given
        Long userId = 1L;

        //when

        //then

        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("User with id = %s not found.", userId));
    }

    @Test
    @DisplayName("Ошибка при изменении пользователя. Должно пройти успешно.")
    void failedUpdatePersonTest() {
        //given

        UserDto userUpdateDto = new UserDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setAge(18);
        userUpdateDto.setFullName("Ed Sheeran");
        userUpdateDto.setTitle("I see fire");

        //when

        //then
        assertThatThrownBy(() -> userService.updateUser(userUpdateDto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("User with id = %s not found.", userUpdateDto.getId()));
    }

    // update
    // get
    // get all
    // delete

    // * failed
    //         doThrow(dataInvalidException).when(testRepository)
    //                .save(same(test));
    // example failed
    //  assertThatThrownBy(() -> testeService.createTest(testRequest))
    //                .isInstanceOf(DataInvalidException.class)
    //                .hasMessage("Invalid data set");
}
