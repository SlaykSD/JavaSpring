package com.edu.ulab.app.validators.update;

import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.update.UserBookUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class UserBookUpdateRequestValidator implements Validator {
    private final BookUpdateRequestValidator bookValidator;
    private final UserUpdateRequestValidator userValidator;
    private  final ValidatorFactory validatorFactory =
            Validation.buildDefaultValidatorFactory();

    public UserBookUpdateRequestValidator(BookUpdateRequestValidator bookValidator, UserUpdateRequestValidator userValidator) {
        this.bookValidator = bookValidator;
        this.userValidator = userValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserBookUpdateRequest.class.equals(clazz);
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
        UserBookUpdateRequest userBookRequest = (UserBookUpdateRequest) target;
        userBookRequest.getBookRequests().forEach(bookRequest -> bookValidator.validateByField(bookRequest,errors,"bookRequests"));
        userValidator.validateByField(userBookRequest.getUserRequest(),errors,"userRequest");
    }
}
