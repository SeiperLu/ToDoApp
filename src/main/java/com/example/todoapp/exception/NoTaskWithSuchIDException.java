package com.example.todoapp.exception;

public class NoTaskWithSuchIDException extends RuntimeException {
    public NoTaskWithSuchIDException(String message) {
        super(message);
    }

    public NoTaskWithSuchIDException() {
        super("No task with such id.");
    }
}
