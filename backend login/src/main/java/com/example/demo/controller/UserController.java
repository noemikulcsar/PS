package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userService.findByUsernameOrThrow(loginUser.getUsername());

        if (!user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        return ResponseEntity.ok(userRepository.save(user));
    }
}