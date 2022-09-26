package com.edu.ulab.app.web;

import com.edu.ulab.app.facade.UserDataFacade;
import com.edu.ulab.app.validators.BookRequestValidator;
import com.edu.ulab.app.validators.UserBookRequestValidator;
import com.edu.ulab.app.validators.update.UserBookUpdateRequestValidator;
import com.edu.ulab.app.web.constant.WebConstant;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.request.BooksRequest;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.update.UserBookUpdateRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.InvalidKeyException;
import javax.validation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import java.util.List;

import static com.edu.ulab.app.web.constant.WebConstant.REQUEST_ID_PATTERN;
import static com.edu.ulab.app.web.constant.WebConstant.RQID;

@Slf4j
@RestController
@RequestMapping(value = WebConstant.VERSION_URL + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserDataFacade userDataFacade;
    private final UserBookRequestValidator userBookRequestValidator;
    private final UserBookUpdateRequestValidator userBookUpdateRequestValidator;
    private final BookRequestValidator bookRequestValidator;

    public UserController(UserDataFacade userDataFacade, UserBookRequestValidator userBookRequestValidator, UserBookUpdateRequestValidator userBookUpdateRequestValidator, BookRequestValidator bookRequestValidator) {
        this.userDataFacade = userDataFacade;
        this.userBookRequestValidator = userBookRequestValidator;
        this.userBookUpdateRequestValidator = userBookUpdateRequestValidator;
        this.bookRequestValidator = bookRequestValidator;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Create user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse createUserWithBooks(@Valid @RequestBody UserBookRequest request,BindingResult bindingResult,
                                                @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {

        userBookRequestValidator.validate(request,bindingResult);

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
                throw new ValidationException(objectError.getDefaultMessage());
            });
        }
        UserBookResponse response = userDataFacade.createUserWithBooks(request);
        log.info("Response with created user and his books: {}", response);
        return response;
    }

    @PutMapping(value = "/update")
    @Operation(summary = "Update user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse updateUserWithBooks(@Valid @RequestBody UserBookUpdateRequest request,BindingResult bindingResult) {
        userBookUpdateRequestValidator.validate(request,bindingResult);

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
                throw new ValidationException(objectError.getDefaultMessage());
            });
        }

        UserBookResponse response = userDataFacade.updateUserWithBooks(request);
        log.info("Response with updated user and his books: {}", response);
        return response;
    }

    @PutMapping(value = "/update/UserBooks/{userId}")
    public UserBookResponse updateUserBooks(@RequestBody @Valid BooksRequest request, BindingResult bindingResult, Long userId) {
        request.getBooks().forEach(bookRequest ->  bookRequestValidator.validateByField(bookRequest,bindingResult,"books"));
        if(userId < 1){
            log.error("Invalid index format: {}", userId);
            throw new ValidationException("Incorrect id");
        }
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
                throw new ValidationException(objectError.getDefaultMessage());
            });
        }

        UserBookResponse response = userDataFacade.updateUserBooks(request.getBooks(),userId);
        log.info("Response with updated user and his books: {}", response);
        return response;
    }

    @GetMapping(value = "/get/{userId}")
    @Operation(summary = "Get user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse getUserWithBooks(@PathVariable Long userId) {
        if(userId < 1){
            log.error("Invalid index format: {}", userId);
            throw new ValidationException("Incorrect id");
        }
        UserBookResponse response = userDataFacade.getUserWithBooks(userId);
        log.info("Response with user and his books: {}", response);
        return response;
    }

    @DeleteMapping(value = "/delete/{userId}")
    @Operation(summary = "Delete user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public void deleteUserWithBooks(@PathVariable Long userId) {
        if(userId<1){
            log.error("Invalid index format: {}", userId);
            throw new ValidationException("Incorrect id");
        }
        log.info("Delete user and his books:  userId {}", userId);
        userDataFacade.deleteUserWithBooks(userId);
    }
}
