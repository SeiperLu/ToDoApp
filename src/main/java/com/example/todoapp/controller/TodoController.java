package com.example.todoapp.controller;


import com.example.todoapp.entity.CustomUserDetails;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.entity.User;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    private final UserRepository userRepository;

    public TodoController(TodoService todoService, UserRepository userRepository) {
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<String> creatTodo(@RequestBody Todo todo) {
        User currentUser = getCurrentUser();
        try {
            todoService.creatTodo(todo,currentUser);
            return ResponseEntity.ok().body("Task created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        User currentUser = getCurrentUser();
        try {
            List<Todo> todos = todoService.getTodosByUser(currentUser);
            return ResponseEntity.ok(todos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodos(@PathVariable long id, @RequestBody Todo todo){
        User currentUser = getCurrentUser();
        try {
            todoService.updateTodo(id, todo, currentUser);
            return ResponseEntity.ok().body("Tasks updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodos(@PathVariable long id){
        User currentUser = getCurrentUser();
        try {
            todoService.deleteTodo(id, currentUser);
            return ResponseEntity.ok().body("Tasks deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
