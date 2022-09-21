package com.edu.ulab.app.exception;

import com.edu.ulab.app.enumiration.ErrorMessage;

public class BookNotFoundException extends NotFoundException{


    public BookNotFoundException( Long id) {
        super(ErrorMessage.BOOK_NOT_FOUND, id);
    }
}
