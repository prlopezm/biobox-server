package mx.com.tecnetia.orthogonal.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotContainsValidator.class)
public @interface NotContains {
    String[] notAllowValues() default {};
    String message() default "";
    Class<?>[] groups() default {}; // 2.
    Class<? extends Payload>[] payload() default {}; // 3.
}
