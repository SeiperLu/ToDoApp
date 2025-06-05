package com.example.todoapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class user {
    @Id long id;
    String username;
    String password;
    String email;
    String role;
}
