package model.validator;


import model.User;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int MIN_PASSWORD_LENGTH = 8;
    private final List<String> errors=new ArrayList<>();
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(String username, String password) {
        errors.clear();
        validateEmailUniqueness(username);
        validateEmail(username);
        validatePasswordLength(password);
        validatePasswordSpecial(password);
        validatePasswordDigit(password);
    }

    private void validateEmailUniqueness(String email) {
        final boolean response = userRepository.existsByUsername(email);
        if (response) {
            errors.add("Email is already taken");
        }
    }

    private void validateEmail(String email) {
        if (!email.matches(EMAIL_VALIDATION_REGEX)) {
            errors.add("Email is not valid");
        }
    }

    private void validatePasswordLength(String password) {
        if (password.length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }
    }

    private void validatePasswordSpecial(String password) {
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            errors.add("Password must contain at least one special character");
        }
    }

    private void validatePasswordDigit(String password) {
        if (!password.matches(".*\\d.*")) {
            errors.add("Password must contain at least one digit");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
    public String getFormattedErrors()
    {
        return String.join("\n",errors);
    }
}