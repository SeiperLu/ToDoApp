package com.example.todoapp.exception;

public class UserDontContainThisTaskException extends RuntimeException {
    public UserDontContainThisTaskException(String message) {
        super(message);
    }

    public UserDontContainThisTaskException() {
        super("User don't contain this task.");
    }
}
