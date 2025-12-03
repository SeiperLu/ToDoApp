package com.example.todoapp.exception;

public class TaskExistsException extends RuntimeException {
    public TaskExistsException(String message) {
        super(message);
    }

    public TaskExistsException() {
        super("That task already exists.");
    }
}
