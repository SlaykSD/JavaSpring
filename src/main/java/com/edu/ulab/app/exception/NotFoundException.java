package com.edu.ulab.app.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }


}
