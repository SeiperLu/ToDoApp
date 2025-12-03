package com.example.todoapp.exception;

public class EmptyTitleException extends RuntimeException {
    public EmptyTitleException(String message) {
        super(message);
    }

    public EmptyTitleException() {
        super("Title is empty.");
    }
}
