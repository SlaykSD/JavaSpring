package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.enumiration.SQL.UserCommandsSQL;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.dto.UserDtoMapper;
import com.edu.ulab.app.mapper.row.UserRowMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate, UserDtoMapper userDtoMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDtoMapper = userDtoMapper;
    }


    @Override
    public UserDto createUser(UserDto userDto) {


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(UserCommandsSQL.INSERT_SQL.getCommand(), new String[]{"person_id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Saved user: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        getUserById(userDto.getId());
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(UserCommandsSQL.UPDATE_SQL.getCommand(), new String[]{"person_id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    ps.setLong(4,  userDto.getId());
                    return ps;
                });

        log.info("Update user: {}", userDto);
        return userDto;
    }


    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);

        Person person = jdbcTemplate.query(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(UserCommandsSQL.SELECT_SQL.getCommand(), new String[]{"person_id"});
                    ps.setLong(1, id);
                    return ps;
                }, new UserRowMapper()).stream().findFirst().orElseThrow(() -> new UserNotFoundException(id));
        log.info("A user was found: {}", person);
        return userDtoMapper.personToUserDto(person);
    }

    @Override
    public void deleteUserById(Long id) {
        UserDto userDto = getUserById(id);
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(UserCommandsSQL.DELETE_SQL.getCommand(), new String[]{"person_id"});
                    ps.setLong(1, userDto.getId());
                    return ps;
                });

        log.info(String.format("The book with id=%d was successfully deleted", id));
    }
}
