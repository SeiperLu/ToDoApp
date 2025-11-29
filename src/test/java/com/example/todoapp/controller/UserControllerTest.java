package com.example.todoapp.controller;

import com.example.todoapp.entity.Todo;
import com.example.todoapp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test") // <-- forces in-memory DB
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private User initUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRole("ROLE_USER");
        return user;
    }

    private Todo initTodo() {
        Todo todo = new Todo();
        todo.setTitle("test");
        todo.setDescription("desc");
        todo.setStatus("done");
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
        todo.setPriority("TestPriority");
        todo.setCompleted(false);
        return todo;
    }

    @Test
    void registerUser_ShouldReturnOk() {
        User user = initUser();
        ResponseEntity<String> response =
                restTemplate.postForEntity("/register", user, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginUser_ShouldReturnOk() {
        User user = initUser();
        restTemplate.postForEntity("/register", user, String.class);
        ResponseEntity<String> response = restTemplate.withBasicAuth("username", "password").getForEntity("/todos", String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginUser_ShouldReturnUnauthorized() {
        User user = initUser();
        restTemplate.postForEntity("/register", user, String.class);
        ResponseEntity<String> response = restTemplate.withBasicAuth("alex", "password123").getForEntity("/todos", String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void createTodo_ShouldReturnOk() {
        User user = initUser();
        restTemplate.postForEntity("/register", user, String.class);
        Todo todo = initTodo();
        ResponseEntity<String> response =
                restTemplate.withBasicAuth("username","password").postForEntity("/todos", todo, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getTodos_ShouldReturnOk() {
        User user = initUser();
        restTemplate.postForEntity("/register", user, String.class);
        ResponseEntity<String> response =
                restTemplate.withBasicAuth("username","password").getForEntity("/todos", String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateTodo_ShouldReturnOk() {
        User user = initUser();
        restTemplate.postForEntity("/register", user, String.class);
        Todo todo = initTodo();
        restTemplate.withBasicAuth("username", "password").postForEntity("/todos",todo, String.class);
        ResponseEntity<Todo[]> getRespond =
                restTemplate.withBasicAuth("username","password").getForEntity("/todos", Todo[].class);
        Assertions.assertNotNull(getRespond.getBody());
        long id = getRespond.getBody()[0].getId();
        Todo updateTodo = initTodo();
        updateTodo.setTitle("new title");
        updateTodo.setDescription("new desc");
        HttpEntity<Todo>  request = new HttpEntity<>(updateTodo);
        ResponseEntity<String> response =
                restTemplate.withBasicAuth("username","password").exchange("/todos/" + id, HttpMethod.PUT, request, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteTodo_ShouldReturnOk() {
        User user = initUser();
        restTemplate.postForEntity("/register", user, String.class);
        Todo todo = initTodo();
        restTemplate.withBasicAuth("username", "password").postForEntity("/todos",todo, String.class);
        ResponseEntity<Todo[]> getRespond =
                restTemplate.withBasicAuth("username","password").getForEntity("/todos", Todo[].class);
        Assertions.assertNotNull(getRespond.getBody());
        long id = getRespond.getBody()[0].getId();
        ResponseEntity<String> response =
                restTemplate.withBasicAuth("username","password").exchange("/todos/"+ id, HttpMethod.DELETE,null, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
