package com.example.demo.service.impl;

import com.example.demo.dto.ticket.TicketCreateDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;
import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.dto.user.UserProfileDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.TicketMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Event;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.websocket.NotificationService;
import com.example.demo.websocket.TicketNotification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final NotificationService notificationService;

    @Override
    public UserDTO addUser(UserCreateDTO dto) {
        User user = UserMapper.fromCreateDto(dto);
        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }

    @Override
    public UserProfileDTO getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return UserMapper.toProfileDto(user);
    }

    @Override
    public UserProfileDTO updateUser(Long id, UserProfileDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        user = UserMapper.fromProfileDto(dto, user);
        User updated = userRepository.save(user);
        return UserMapper.toProfileDto(updated);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDetailsDTO> getUserTickets(Long id) {
        User user = getUserById(id);
        return user.getTickets().stream()
                .map(TicketMapper::toDetailsDto)
                .toList();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User authenticate(String username, String password) {
        return userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    public TicketDetailsDTO addTicketToUser(Long userId, TicketCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + dto.getEventId()));

        int newSoldTickets = event.getSoldTickets() + 1;
        event.setSoldTickets(newSoldTickets);

        char rowLetter = (char) ('A' + ((newSoldTickets - 1) / 10));
        int seatNum = ((newSoldTickets - 1) % 10) + 1;
        String generatedSeat = String.valueOf(rowLetter) + seatNum;

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setPrice(event.getPrice());
        ticket.setSeatNumber(generatedSeat);

        Ticket saved = ticketRepository.save(ticket);
        notificationService.sendTicketNotification(
                new TicketNotification(user.getUsername(), event.getName(), generatedSeat)
        );
        return TicketMapper.toDetailsDto(saved);
    }
    @Override
    public String uploadProfilePicture(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get("uploads");

        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "http://localhost:8080/uploads/" + fileName;
            user.setProfilePictureUrl(fileUrl);
            userRepository.save(user);

            return fileUrl;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }
}
