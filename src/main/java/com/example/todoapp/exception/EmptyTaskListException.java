package com.example.todoapp.exception;

public class EmptyTaskListException extends RuntimeException {
    public EmptyTaskListException(String message) {
        super(message);
    }

    public EmptyTaskListException() {
        super("Task list is empty.");
    }
}
