package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public User authenticate(String username, String password) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
    @Override
    public User registerUser(User newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        return userRepository.save(newUser);
    }
    public User findByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }
}