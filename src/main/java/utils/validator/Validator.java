package utils.validator;

import exception.ValidateException;

/**
 * @author Oleksandr Pavelko
 */
public class Validator {
    static final String PASSWORD_PATTERN_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    static final String NAME_PATTERN_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'-]{1,30}";
    static final String EMAIL_PATTERN_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    ;

    private Validator() {
    }

    public static void validatePassword(String password) throws ValidateException {
        if (!password.matches(PASSWORD_PATTERN_REGEX)) {
            throw new ValidateException("passwordIncorrect");
        }
    }

    public static void validateEmail(String email) throws ValidateException {
        if (!email.matches(EMAIL_PATTERN_REGEX)) {
            throw new ValidateException("emailIncorrect");
        }
    }

    public static void validateName(String name) throws ValidateException {
        if (!name.matches(NAME_PATTERN_REGEX)) {
            throw new ValidateException("nameIncorrect");
        }
    }

    public static void validateOrderLocations(String loc_to, String loc_from) throws ValidateException {
        if (loc_from == null || loc_to == null) {
            throw new ValidateException("locationNotValid");
        }
    }

    public static void validatePassengers(String passengers) throws ValidateException {
        if (passengers == null) {
            throw new ValidateException("passengersNotValid");
        }
    }


}
