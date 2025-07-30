package com.fortunaglobal.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a request conflicts with the current state of the system.
 * Automatically returns HTTP 409 (Conflict) status when thrown from controller methods.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    /**
     * Constructs a new conflict exception with the specified detail message.
     *
     * @param message the detail message explaining the conflict
     */
    public ConflictException(String message) {
        super(message);
    }

    /**
     * Constructs a new conflict exception with the specified detail message and cause.
     *
     * @param message the detail message explaining the conflict
     * @param cause the underlying cause of the conflict
     */
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new conflict exception with formatted message using String.format().
     *
     * @param format the format string
     * @param args arguments referenced by the format specifiers in the format string
     */
    public ConflictException(String format, Object... args) {
        super(String.format(format, args));
    }
}
