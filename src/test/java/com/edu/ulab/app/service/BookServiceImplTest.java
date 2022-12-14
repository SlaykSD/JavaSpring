package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;

import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.dto.BookDtoMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
public class BookServiceImplTest {
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookDtoMapper bookMapper;

    @Test
    @DisplayName("Создание книги. Должно пройти успешно.")
    void saveBookTest() {
        //given
        Person person  = new Person();
        person.setId(1001L);

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(500);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1001L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(500);

        Book book = new Book();
        book.setPageCount(500);
        book.setAuthor("test author");
        book.setTitle("test title");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(500);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.createBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals(1001L, bookDtoResult.getUserId());
        assertEquals(500, bookDtoResult.getPageCount());
        assertEquals("test author", bookDtoResult.getAuthor() );
        assertEquals("test title", bookDtoResult.getTitle() );
    }


    // update
    @Test
    @DisplayName("Изменение книги. Должно пройти успешно.")
    void updateBookTest() {
        //given
        Person person  = new Person();
        person.setId(1001L);

        BookDto bookDtoUpdate = new BookDto();
        bookDtoUpdate.setId(1L);
        bookDtoUpdate.setUserId(1001L);
        bookDtoUpdate.setAuthor("Ed Sheeran");
        bookDtoUpdate.setTitle("I see fire");
        bookDtoUpdate.setPageCount(10);

        Book book = new Book();
        book.setPageCount(500);
        book.setAuthor("test author");
        book.setTitle("test title");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        bookDtoUpdate.setPageCount(10);
        savedBook.setTitle("I see fire");
        savedBook.setAuthor("Ed Sheeran");
        savedBook.setPerson(person);

        Book updateBook = new Book();
        updateBook.setId(1L);
        updateBook.setPageCount(10);
        updateBook.setTitle("I see fire");
        updateBook.setAuthor("Ed Sheeran");
        updateBook.setPerson(person);

        //when

        when(bookRepository.findByIdForUpdate(bookDtoUpdate.getId())).thenReturn(Optional.of(book));
        when(bookMapper.bookDtoToBook(bookDtoUpdate)).thenReturn(updateBook);
        when(bookRepository.save(updateBook)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(bookDtoUpdate);


        //then
        BookDto bookDtoResult = bookService.updateBook(bookDtoUpdate);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals(1001L, bookDtoResult.getUserId());
        assertEquals(10,bookDtoResult.getPageCount());
        assertEquals("Ed Sheeran", bookDtoResult.getAuthor());
        assertEquals("I see fire", bookDtoResult.getTitle());
    }
    // get
    @Test
    @DisplayName("Выдать книги. Должно пройти успешно.")
    void getBookTest() {
        //given
        Person person  = new Person();
        person.setId(1001L);

        Long bookId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1001L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(savedBook));
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.getBookById(bookId);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals("test title", bookDtoResult.getTitle());
        assertEquals("test author", bookDtoResult.getAuthor());
        assertEquals(1001L, bookDtoResult.getUserId());
        assertEquals(1000, bookDtoResult.getPageCount());
    }
    // get all

    @Test
    @DisplayName("Выдать все книги по id юзера. Должно пройти успешно.")
    void getAllBookTest() {
        //given
        Person person  = new Person();
        person.setId(1L);

        Long personId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        BookDto result2 = new BookDto();
        result2.setId(1L);
        result2.setUserId(1L);
        result2.setAuthor("test author");
        result2.setTitle("test title");
        result2.setPageCount(1000);

        Book savedBook2 = new Book();
        savedBook2.setId(1L);
        savedBook2.setPageCount(1000);
        savedBook2.setTitle("test title");
        savedBook2.setAuthor("test author");
        savedBook2.setPerson(person);

        List<Book> books = new ArrayList<>();
        books.add(savedBook);
        books.add(savedBook2);
        //when

        when(bookRepository.findBooksByUserId(personId)).thenReturn(books);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);
        when(bookMapper.bookToBookDto(savedBook2)).thenReturn(result2);

        //then
        List<BookDto> bookDtoResult = bookService.getBooksByUserId(personId);
        assertEquals(bookDtoResult.size(), 2);
        assertEquals(bookDtoResult.get(0), result);
    }
    // delete
    @Test
    @DisplayName("Удалить книгу. Должно пройти успешно.")
    void deleteBookTest() {
        //given
        Long bookId = 1L;

        Person person  = new Person();
        person.setId(bookId);

        Book book = new Book();
        book.setId(bookId);
        book.setPageCount(500);
        book.setAuthor("test author");
        book.setTitle("test title");
        book.setPerson(person);

        BookDto result = new BookDto();
        result.setId(bookId);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);


        //when
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookService.getBookById(bookId)).thenReturn(result);
        doNothing().when(bookRepository).deleteById(bookId);

        //then
        bookService.deleteBookById(bookId);
    }
    // * failed

    @Test
    @DisplayName("Ошибка при выдачи книги. Должно пройти успешно.")
    void failedGetBookTest() {
        //given
        Person person  = new Person();
        person.setId(1L);

        Long bookId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when


        //then
        assertThatThrownBy(() -> bookService.getBookById(bookId))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage(String.format("Book with id = %s not found.",bookId));
    }

    // update
    // get
    // get all
    // delete

    // * failed
}
