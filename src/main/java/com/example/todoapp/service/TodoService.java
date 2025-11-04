package com.example.todoapp.service;

import com.example.todoapp.entity.Todo;
import com.example.todoapp.entity.User;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void creatTodo(Todo todo, User user) {
        todo.setTitle(todo.getTitle().trim());
        todo.setDescription(todo.getDescription().trim());
        if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
            throw new RuntimeException("Title is empty");
        }
        if (todoRepository.existsByTitleAndUser(todo.getTitle(),user)) {
            throw new RuntimeException("That task already exists");
        }
        todo.setUser(user);
        todoRepository.save(todo);
    }

    public List<Todo> getTodosByUser(User user) {
        return todoRepository.findByUser(user);
    }

    public void updateTodo(long id, Todo updatedTodo, User user) {
        if (todoRepository.findByUser(user).isEmpty()) {
            throw new RuntimeException("Task list is empty");
        }
        if (!todoRepository.findById(id).orElseThrow(() -> new RuntimeException("No task with such id.")).getUser().equals(user)) {
            throw new RuntimeException("Task id don't match with user");
        }
        if (updatedTodo.getTitle() == null || updatedTodo.getTitle().isEmpty()) {
            throw new RuntimeException("Title is empty");
        }
        Todo existing =  todoRepository.findById(id).orElseThrow(() ->new RuntimeException("Not found"));
        if (existing.getUser().equals(user)) {
            existing.setTitle(updatedTodo.getTitle());
            existing.setDescription(updatedTodo.getDescription());
            existing.setPriority(updatedTodo.getPriority());
            existing.setStatus(updatedTodo.getStatus());
            existing.setCompleted(updatedTodo.isCompleted());
            existing.setUpdatedAt(updatedTodo.getUpdatedAt());

        }
        todoRepository.save(existing);
    }

    public void deleteTodo(long id, User user) {
        if (!todoRepository.findById(id).orElseThrow(() -> new RuntimeException("No task with such id.")).getUser().equals(user)) {
            throw new RuntimeException("User don't contain this task");
        }
        todoRepository.deleteById(id);
    }


}
