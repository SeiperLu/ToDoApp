package com.example.todoapp.exception;

public class TaskIDMismatchWithUserException extends RuntimeException {
    public TaskIDMismatchWithUserException(String message) {
        super(message);
    }

    public TaskIDMismatchWithUserException() {
        super("Task id don't match with user.");
    }
}
