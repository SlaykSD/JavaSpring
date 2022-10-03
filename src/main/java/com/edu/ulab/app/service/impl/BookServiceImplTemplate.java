package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.enumiration.SQL.BookCommandsSQL;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.mapper.dto.BookDtoMapper;
import com.edu.ulab.app.mapper.row.BookRowMapper;
import com.edu.ulab.app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;
    private final BookDtoMapper bookDtoMapper;



    @Override
    @Transactional
    public BookDto createBook(BookDto bookDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(BookCommandsSQL.INSERT_SQL.getCommand(), new String[]{"book_id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getUserId());
                    return ps;
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Saved book: {}", bookDto);
        return bookDto;
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        getBookById(bookDto.getId());
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(BookCommandsSQL.UPDATE_SQL.getCommand(), new String[]{"book_id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getUserId());
                    ps.setLong(5, bookDto.getId());
                    return ps;
                });

        log.info("Updated book: {}", bookDto);
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Got book by id: {}", id);

        Book book = jdbcTemplate.query(
                connection -> {
            PreparedStatement ps = connection.prepareStatement(BookCommandsSQL.SELECT_SQL.getCommand(), new String[]{"book_id"});
            ps.setLong(1, id);
            return ps;
        }, new BookRowMapper()).stream().findFirst().orElseThrow(() -> new BookNotFoundException(id));
        log.info("A book was found {}", book);
        return bookDtoMapper.bookToBookDto(book);
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        BookDto bookDto = getBookById(id);
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(BookCommandsSQL.DELETE_SQL.getCommand(), new String[]{"book_id"});
                    ps.setLong(1, bookDto.getId());
                    return ps;
                });

        log.info(String.format("The book with id=%d was successfully deleted", id));
    }

    @Override
    public List<BookDto> getBooksByUserId(Long id) {
        log.info("Get user books by userID: {}", id);

        List<Book> books = jdbcTemplate.query(
                connection -> {
                    PreparedStatement ps = connection
                            .prepareStatement(BookCommandsSQL.SELECT_BY_USER_ID_SQL.getCommand(), new String[]{"book_id"});
                    ps.setLong(1, id);
                    return ps;
                }, new BookRowMapper()).stream().toList();
        log.info("A books list was found: {}", books);
        return books.stream().map(bookDtoMapper::bookToBookDto).toList();
    }

    @Override
    public List<Long> getBooksIdByUserId(Long id) {
        log.info("Get user booksID by userID: {}", id);

        List<Book> books = jdbcTemplate.query(
                connection -> {
                    PreparedStatement ps = connection
                            .prepareStatement(BookCommandsSQL.SELECT_BY_USER_ID_SQL.getCommand(), new String[]{"book_id"});
                    ps.setLong(1, id);
                    return ps;
                }, new BookRowMapper()).stream().toList();
        log.info("A books list was found: {}", books);
        return books.stream().map(book -> book.getId().longValue()).toList();
    }


}
