package com.example.todoapp.exception;

public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException(String message){
        super(message);
    }

    public UsernameTakenException(){
        super("Username is already taken.");
    }
}
