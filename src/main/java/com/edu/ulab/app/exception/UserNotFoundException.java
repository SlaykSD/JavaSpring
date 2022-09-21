package com.edu.ulab.app.exception;

import com.edu.ulab.app.enumiration.ErrorMessage;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(Long id) {
        super(ErrorMessage.USER_NOT_FOUND, id);
    }
}
