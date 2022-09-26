package com.edu.ulab.app.mapper.row;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book =  new Book();
        book.setId(rs.getLong("book_id"));
        book.setAuthor(rs.getString("author"));
        book.setUserId(rs.getLong("user_id"));
        book.setTitle(rs.getString("title"));
        book.setPageCount(rs.getInt("page_count"));
        return book ;
    }
}
