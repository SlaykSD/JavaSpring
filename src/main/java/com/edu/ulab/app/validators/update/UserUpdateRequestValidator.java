package com.edu.ulab.app.validators.update;

import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.request.update.UserUpdateRequest;
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
    public boolean supports(Class<?> clazz) {
        return UserUpdateRequest.class.equals(clazz);
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


    }
}
