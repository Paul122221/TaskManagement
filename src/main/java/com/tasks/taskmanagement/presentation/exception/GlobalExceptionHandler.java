package com.tasks.taskmanagement.presentation.exception;

import com.tasks.taskmanagement.application.spring.exception.TaskValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for managing and handling various exceptional situations in the application.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles the EntityNotFoundException, which occurs when an entity is not found in the database.
     * @param ex The EntityNotFoundException to be handled.
     * @return ResponseEntity with HttpStatus.NOT_FOUND and an error message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Entity not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles the MethodArgumentNotValidException, which occurs when there are invalid method arguments in a controller method.
     * @param ex The MethodArgumentNotValidException to be handled.
     * @return ResponseEntity with HttpStatus.BAD_REQUEST and a list of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        log.error("Validation errors: {}", errors, ex);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Converts a list of errors into a Map format for the response.
     * @param errors The list of validation errors.
     * @return A Map containing the list of errors.
     */
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    /**
     * Handles the RequestNotModifiableException, which occurs when attempting to modify an unmodifiable request.
     * @param ex The RequestNotModifiableException to be handled.
     * @return ResponseEntity with HttpStatus.BAD_REQUEST and an error message.
     */
    @ExceptionHandler(RequestNotModifiableException.class)
    public ResponseEntity<String> handleRequestNotModifiableException(RequestNotModifiableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles the TaskValidationException, which occurs when task validation fails.
     * @param ex The TaskValidationException to be handled.
     * @return ResponseEntity with HttpStatus.BAD_REQUEST and an error message.
     */
    @ExceptionHandler(TaskValidationException.class)
    public ResponseEntity<String> handleTaskValidationException(TaskValidationException ex) {
        log.error("errors: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
