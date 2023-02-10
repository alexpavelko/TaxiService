package utils;


//

import exception.ValidateException;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.validator.Validator;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void testValidateCorrectPassword() throws ValidateException {
        String password = "Test1234";
        Validator.validatePassword(password);
    }

    @ParameterizedTest
    @MethodSource("invalidPasswordValues")
    public void testValidateIncorrectPassword(String password) {

        Throwable thrown = assertThrows(ValidateException.class, () -> {
            Validator.validatePassword(password);
        });

        assertEquals(thrown.getMessage(), "passwordIncorrect");
    }

    @Test
    public void testValidateCorrectEmail() throws ValidateException {
        String email = "test@example.com";
        Validator.validateEmail(email);
    }

    @Test
    public void testValidateIncorrectEmail() throws ValidateException {
        String incorrectEmail = "test@@@example.com";
        Throwable thrown = assertThrows(ValidateException.class, () -> {
            Validator.validateEmail(incorrectEmail);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testValidateName() throws ValidateException {
        String name = "AlexPavelko";
        Validator.validateName(name);
    }

    @Test(expected = ValidateException.class)
    public void testValidateIncorrectName() throws ValidateException {
        String name = "123";
        Validator.validateName(name);
    }

    @Test
    public void testValidateOrderLocations() throws ValidateException {
        String loc_to = "New York";
        String loc_from = "Boston";
        Validator.validateOrderLocations(loc_to, loc_from);
    }

    @Test
    public void testValidatePassengers() throws ValidateException {
        int passengers = 4;
        Validator.validatePassengers(passengers);
    }

    private static Stream<Arguments> invalidPasswordValues() {
        return Stream.of(
                Arguments.of("easy"),
                Arguments.of("easy1"),
                Arguments.of("Easy1"),
                Arguments.of("easyeasy"),
                Arguments.of("Easyeasy"),
                Arguments.of("Easy easy"),
                Arguments.of("easyeasy1")

        );
    }

}