package com.edu.ulab.app.validators;

import com.edu.ulab.app.web.request.UserBookRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Objects;
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
    public boolean supports(@Nullable Class<?> clazz) {
        return UserBookRequest.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, @Nullable Errors errors) {
        javax.validation.Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(target);
        for (ConstraintViolation<Object> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            if (errors != null) {
                errors.rejectValue(propertyPath, "", message);
            }
        }
        UserBookRequest userBookRequest = (UserBookRequest) target;
        Objects.requireNonNull(userBookRequest).getBookRequests().forEach(bookRequest -> bookValidator.validateByField(bookRequest,errors,"bookRequests"));
        userValidator.validateByField(userBookRequest.getUserRequest(),errors,"userRequest");
    }
}
