package edu.profitsoft.musicreviewer.annotation;

import edu.profitsoft.musicreviewer.validator.YearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearValidator.class)
@Documented
public @interface ValidYear {
    String message() default "Invalid year!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};
}
