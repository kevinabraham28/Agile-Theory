package com.app;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Event Registration System Tests")
class AppTest {

    private App app;

    @BeforeEach
    void setUp() {
        app = new App();
    }

    @Test
    void shouldRegisterUser() {
        assertTrue(app.registerUser(1, "John Doe", "john@example.com", 25));
        assertEquals(1, app.getRegisteredCount());
    }

    @Test
    void shouldPreventDuplicateEmail() {
        app.registerUser(1, "John Doe", "john@example.com", 25);

        DuplicateRegistrationException ex = assertThrows(
                DuplicateRegistrationException.class,
                () -> app.registerUser(2, "John Smith", "JOHN@EXAMPLE.COM", 30)
        );

        assertTrue(ex.getMessage().contains("john@example.com"));
    }

    @Test
    void shouldPreventDuplicateId() {
        app.registerUser(1, "John Doe", "john@example.com", 25);

        assertThrows(
                DuplicateRegistrationException.class,
                () -> app.registerUser(1, "Jane Doe", "jane@example.com", 30)
        );
    }

    @Test
    void shouldRejectInvalidEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> app.registerUser(1, "John", "invalid", 25)
        );
    }

    @Test
    void shouldUnregisterUser() {
        app.registerUser(1, "John Doe", "john@example.com", 25);
        assertTrue(app.unregisterUser(1, "john@example.com"));
        assertEquals(0, app.getRegisteredCount());
    }
}
