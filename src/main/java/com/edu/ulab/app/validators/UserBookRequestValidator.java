package com.edu.ulab.app.validators;

import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class UserBookRequestValidator implements Validator {
    private final BookRequestValidator bookValidator;
    private final UserRequestValidator userValidator;
    private  final ValidatorFactory validatorFactory =
            Validation.buildDefaultValidatorFactory();

    public UserBookRequestValidator(BookRequestValidator bookValidator, UserRequestValidator userValidator) {
        this.bookValidator = bookValidator;
        this.userValidator = userValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserBookRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        javax.validation.Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(target);
        for (ConstraintViolation<Object> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }
        UserBookRequest userBookRequest = (UserBookRequest) target;
        userBookRequest.getBookRequests().forEach(bookRequest -> bookValidator.validate(bookRequest,errors));
        userValidator.validate(userBookRequest.getUserRequest(),errors);
    }
}
