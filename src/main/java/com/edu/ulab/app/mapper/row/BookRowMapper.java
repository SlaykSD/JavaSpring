package com.edu.ulab.app.mapper.row;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book =  new Book();
        book.setId(rs.getLong("book_id"));
        book.setAuthor(rs.getString("author"));
        Person person = new Person();
        person.setId(rs.getLong("user_id"));
        book.setPerson(person);
        book.setTitle(rs.getString("title"));
        book.setPageCount(rs.getInt("page_count"));
        return book ;
    }
}
