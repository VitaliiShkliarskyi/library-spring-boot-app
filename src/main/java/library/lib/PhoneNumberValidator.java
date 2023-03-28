package library.lib;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String PHONE_NUMBER_VALIDATION_REGEX = "^\\+?[1-9][0-9]{7,14}$";

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        return field != null && field.matches(PHONE_NUMBER_VALIDATION_REGEX);
    }
}
