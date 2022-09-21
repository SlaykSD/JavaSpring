package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.request.BookMapper;
import com.edu.ulab.app.mapper.request.UserMapper;
import com.edu.ulab.app.mapper.request.update.BookUpdateMapper;
import com.edu.ulab.app.mapper.request.update.UserUpdateMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.update.UserBookUpdateRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final BookUpdateMapper bookUpdateMapper;
    private final UserUpdateMapper userUpdateMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper,
                          BookUpdateMapper bookUpdateMapper,
                          UserUpdateMapper userUpdateMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.bookUpdateMapper = bookUpdateMapper;
        this.userUpdateMapper = userUpdateMapper;
    }


    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookUpdateRequest userBookRequest) {
        log.info("Got user and book update request: {}", userBookRequest);
        UserDto userDto =  userService.updateUser(userUpdateMapper
                .userUpdateRequestToUserDto(userBookRequest.getUserRequest()));
        log.info("Mapped user request: {}", userDto);
        List<Long> listBooksId = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookUpdateMapper::bookUpdateRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(userDto.getId()))
                .map(bookService::updateBook)
                .peek(bookDtoUpdated -> log.info(bookDtoUpdated.toString()))
                .map(BookDto::getId)
                .toList();
        log.info("Collected updates book ids: {}", listBooksId);
        return UserBookResponse.builder().userId(userDto.getId()).booksIdList(listBooksId).build();

    }
    public UserBookResponse updateUserBooks(List<BookRequest> userBookRequest, Long userId) {
        log.info("Got user books update request: {}", userBookRequest);
        UserDto userDto =  userService.getUserById(userId);
        log.info("Delete old books user: {}", userDto);
        bookService.getBooksByUserId(userId).stream()
                .peek(bookFound -> log.info("Found the user's book: {}", bookFound))
                .forEach(book-> bookService.deleteBookById(book.getId()));
        log.info("Delete old books user");
        List<Long> bookIdList = userBookRequest
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(userDto.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();

    }
    public UserBookResponse getUserWithBooks(Long userId) {

        log.info("Got user book get request: {}", userId);
        UserDto userDto = userService.getUserById(userId);

        List<Long> bookIdList = bookService.getBooksByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .peek(bookDto -> log.info("Got book: {}",bookDto))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Got user book delete request: {}", userId);
        userService.deleteUserById(userId);
        log.info("Delete user with id: {}", userId);
        bookService.getBooksByUserId(userId).stream()
                .peek(bookFound -> log.info("Found the user's book: {}", bookFound))
                .forEach(book-> bookService.deleteBookById(book.getId()));
        log.info("Delete is done");
    }
}
