package com.tasks.taskmanagement.presentation.exception;

/**
 * Custom exception class for indicating that a request is not modifiable.
 * This exception is typically thrown when an attempt is made to modify an unmodifiable request.
 */
public class RequestNotModifiableException extends RuntimeException {

    /**
     * Constructs a new RequestNotModifiableException with the specified error message.
     *
     * @param message The error message to associate with this exception.
     */
    public RequestNotModifiableException(String message) {
        super(message);
    }
}
