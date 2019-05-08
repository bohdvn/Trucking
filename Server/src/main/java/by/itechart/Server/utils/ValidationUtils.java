package by.itechart.Server.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationUtils {
    private static final ValidatorFactory factory;
    private static final Validator validator;
    static {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    /**
     * Validate some element
     *
     * @param element element we want to validate
     */
    public static <T> void  validate(final T element){
        Set<ConstraintViolation<T>> violations = validator.validate(element);
        for (ConstraintViolation<T> violation : violations) {
            //do something
        }
    }
}
