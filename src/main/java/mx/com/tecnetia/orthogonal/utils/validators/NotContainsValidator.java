package mx.com.tecnetia.orthogonal.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class NotContainsValidator implements ConstraintValidator<NotContains,String> {

    private String[] notAllowValues;

    @Override
    public void initialize(NotContains constraintAnnotation) {
        notAllowValues = constraintAnnotation.notAllowValues();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !List.of(notAllowValues).contains(value);
    }
}
