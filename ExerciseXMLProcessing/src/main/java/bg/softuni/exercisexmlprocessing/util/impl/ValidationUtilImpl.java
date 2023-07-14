package bg.softuni.exercisexmlprocessing.util.impl;

import bg.softuni.exercisexmlprocessing.util.ValidationUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl() {
       validator = Validation
               .buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}
