package com.example.todoapp.service;


import com.example.todoapp.entity.CustomUserDetails;
import com.example.todoapp.exception.EmailTakenException;
import com.example.todoapp.exception.UserNotFoundException;
import com.example.todoapp.exception.UsernameTakenException;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new UsernameTakenException();
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new EmailTakenException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());
        return new CustomUserDetails(user);
    }
}
