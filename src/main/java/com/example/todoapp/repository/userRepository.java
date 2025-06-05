package com.example.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

interface userRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
