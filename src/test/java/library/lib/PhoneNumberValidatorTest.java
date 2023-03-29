package library.lib;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PhoneNumberValidatorTest {
    private static ConstraintValidatorContext constraintValidatorContext;
    private static PhoneNumberValidator phoneNumberValidator;

    @BeforeAll
    static void beforeAll() {
        phoneNumberValidator = new PhoneNumberValidator();
    }

    @Test
    void isValid_validPhoneNumber_Ok() {
        Assertions.assertTrue(phoneNumberValidator.isValid("+380981245678",
                constraintValidatorContext));
    }

    @Test
    void isValid_invalidPhoneNumber_NotOk() {
        Assertions.assertFalse(phoneNumberValidator.isValid("+2301+3",
                constraintValidatorContext));
    }

    @Test
    void isValid_nullPhoneNumber_NotOk() {
        Assertions.assertFalse(phoneNumberValidator.isValid(null, constraintValidatorContext));
    }
}
