package com.tasks.taskmanagement.application.spring.exception;

/**
 * The TaskValidationException class is a custom exception that extends the RuntimeException class.
 * It is used to indicate a validation error related to tasks in the application.
 */
public class TaskValidationException extends RuntimeException {

    /**
     * Constructs a new TaskValidationException with the specified error message.
     *
     * @param message The error message that describes the validation error.
     */
    public TaskValidationException(String message) {
        super(message);
    }
}
