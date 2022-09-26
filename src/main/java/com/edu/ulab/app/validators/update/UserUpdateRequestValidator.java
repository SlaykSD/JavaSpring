package com.edu.ulab.app.validators.update;

import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.request.update.UserUpdateRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class UserUpdateRequestValidator implements Validator {
    private  final ValidatorFactory validatorFactory =
            Validation.buildDefaultValidatorFactory();

    @Override
    public boolean supports(@Nullable Class<?> clazz) {
        return UserUpdateRequest.class.equals(clazz);
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
    }

    public void validateByField(Object target, Errors errors,String field) {
        javax.validation.Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(target);
        for (ConstraintViolation<Object> violation : violations) {
            String message = violation.getMessage();
            errors.rejectValue(field, "", message);
        }
    }
}
