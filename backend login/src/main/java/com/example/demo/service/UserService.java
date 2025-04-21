package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    User getUserById(Long id);
    User findByUsername(String username);
    User authenticate(String username, String password);
}
