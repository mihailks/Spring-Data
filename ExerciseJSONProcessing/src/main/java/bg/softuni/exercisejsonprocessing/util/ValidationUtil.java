package bg.softuni.exercisejsonprocessing.util;

import jakarta.validation.Validator;
import org.springframework.stereotype.Component;


public interface ValidationUtil {

    <E> boolean isValid(E entity);
}
