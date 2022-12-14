package com.edu.ulab.app.mapper.row;

import com.edu.ulab.app.entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getLong("person_id"));
        person.setFullName(rs.getString("full_name"));
        person.setTitle(rs.getString("title"));
        person.setAge(rs.getInt("age"));
        return person;
    }
}
