package utils.validator;

import exception.ValidateException;

/**
 * @author Oleksandr Pavelko
 */
public class Validator {
    private Validator() {
    }

    public static void validateLogin(String login) throws ValidateException {
        if (!login.matches(LOGIN_PATTERN_REGEX)) {
            throw new ValidateException(LOGIN_EXCEPTION_MESSAGE);
        }
    }

    public static void validatePassword(String password) throws ValidateException {
        if (!password.matches(PASSWORD_PATTERN_REGEX)) {
            throw new ValidateException(PASSWORD_EXCEPTION_MESSAGE);
        }
    }

    public static void validateEmail(String email) throws ValidateException {
        if (!email.matches(EMAIL_PATTERN_REGEX)) {
            throw new ValidateException(EMAIL_EXCEPTION_MESSAGE);
        }
    }

    public static void validateNameAndSurname(String name, String surname) throws ValidateException {
        if (!name.matches(NAME_PATTERN_REGEX) || !surname.matches(NAME_PATTERN_REGEX)) {
            throw new ValidateException(NAME_EXCEPTION_MESSAGE);
        }
    }

    public static void validateOrderLocations(String loc_to, String loc_from) {

    }


}
