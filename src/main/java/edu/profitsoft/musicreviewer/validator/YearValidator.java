package edu.profitsoft.musicreviewer.validator;

import edu.profitsoft.musicreviewer.annotation.ValidYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {
    @Override
    public void initialize(ValidYear constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        return validateYear(year);
    }

    private boolean validateYear(Integer year) {
        return year >= 1900 && year <= 2024;
    }
}
