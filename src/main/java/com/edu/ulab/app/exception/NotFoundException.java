package com.edu.ulab.app.exception;

import com.edu.ulab.app.enumiration.ErrorMessage;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorMessage errorMessage, Long id) {
        super(String.format(errorMessage.getMessage(), id));
    }


}
