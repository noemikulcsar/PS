package com.example.demo.service;

import com.example.demo.dto.ticket.TicketCreateDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserProfileDTO;
import com.example.demo.dto.ticket.TicketDTO;
import com.example.demo.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDTO addUser(UserCreateDTO dto);
    UserProfileDTO getUserProfile(Long id);
    UserProfileDTO updateUser(Long id, UserProfileDTO dto);
    List<UserDTO> getAllUsers();
    List<TicketDetailsDTO> getUserTickets(Long userId);

    User getUserById(Long id);
    User findByUsername(String username);
    User authenticate(String username, String password);
    void deleteUser(Long id);
    TicketDetailsDTO addTicketToUser(Long userId, TicketCreateDTO ticketDto);
    String uploadProfilePicture(Long userId, MultipartFile file);
}
