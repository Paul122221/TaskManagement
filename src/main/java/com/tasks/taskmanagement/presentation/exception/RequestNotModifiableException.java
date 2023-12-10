package com.tasks.taskmanagement.presentation.exception;

public class RequestNotModifiableException extends RuntimeException {

    public RequestNotModifiableException(String message) {
        super(message);
    }
}