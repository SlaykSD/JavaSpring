package com.edu.ulab.app.enumiration;

public enum ErrorMessage {
    BOOK_NOT_FOUND("Book with id = %s not found."),

    USER_NOT_FOUND("User with id = %s not found."),

    TECHNICAL_ERROR("A technical error has occurred."),

    VALIDATION_ERROR("Request validation error.");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
