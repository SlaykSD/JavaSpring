package com.edu.ulab.app.web.handler;

import com.edu.ulab.app.enumiration.ErrorMessage;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.web.response.BaseWebResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final NotFoundException exc) {
        log.error(exc.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler({ValidationException.class, HttpMessageNotReadableException.class,NotReadablePropertyException.class})
    public ResponseEntity<BaseWebResponse> handleValidationExceptions(Exception e) {
        log.error("Throws exception: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(e)));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleOtherExceptions(Exception e) {
        log.error("Throws exception: ", e);
        return new ResponseEntity<>(
                ErrorMessage.TECHNICAL_ERROR.getMessage(),
                HttpStatus.valueOf(500)
        );
    }
    private String createErrorMessage(Exception exception) {
        final String message = exception.getMessage();
        log.error(ExceptionHandlerUtils.buildErrorMessage(exception));
        return message;
    }
}
