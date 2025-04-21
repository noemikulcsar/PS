package com.example.demo.service;

import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<UserDTO> getAllUsers();
    User getUserById(Long id);
    void deleteUser(Long id);

    User updateUser(User user);

    User findByUsername(String username);
    Ticket addTicketToUser(Long userId, Ticket ticket);
    List<TicketDTO> getUserTickets(Long userId);
}
