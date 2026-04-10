package com.app;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class App {

    private final Set<String> registeredEmails = new HashSet<>();
    private final Set<Long> registeredIds = new HashSet<>();

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    private boolean isValidEmail(String email) {
        return email != null && !email.trim().isEmpty() &&
               Pattern.matches(EMAIL_REGEX, email);
    }

    private boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }

    private boolean isValidAge(int age) {
        return age >= 18 && age <= 120;
    }

    public boolean registerUser(long id, String name, String email, int age) {

        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (!isValidAge(age)) {
            throw new IllegalArgumentException("Invalid age");
        }

        String normalizedEmail = email.toLowerCase();

        if (registeredIds.contains(id)) {
            throw new DuplicateRegistrationException("User with ID " + id + " is already registered");
        }

        if (registeredEmails.contains(normalizedEmail)) {
            throw new DuplicateRegistrationException("Email " + normalizedEmail + " is already registered");
        }

        registeredIds.add(id);
        registeredEmails.add(normalizedEmail);

        System.out.println("User registered successfully: " + name + " (" + email + ")");
        return true;
    }

    public int getRegisteredCount() {
        return registeredIds.size();
    }

    public boolean unregisterUser(long id, String email) {
        String normalizedEmail = email.toLowerCase();

        if (registeredIds.remove(id)) {
            registeredEmails.remove(normalizedEmail);
            return true;
        }
        return false;
    }

    public boolean isRegistered(long id) {
        return registeredIds.contains(id);
    }

    public static void main(String[] args) {
        App app = new App();

        try {
            app.registerUser(1, "John Doe", "john@example.com", 25);
            app.registerUser(2, "Jane Smith", "jane@example.com", 30);

            System.out.println("Total registered: " + app.getRegisteredCount());

            Thread.sleep(Long.MAX_VALUE);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

class DuplicateRegistrationException extends RuntimeException {
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
