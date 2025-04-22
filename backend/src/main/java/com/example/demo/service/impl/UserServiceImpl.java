package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Event;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.dto.TicketDTO;
import com.example.demo.mapper.TicketMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public User updateUser(User user) {
        User existing = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + user.getId()));

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        existing.setAddress(user.getAddress());
        existing.setUsername(user.getUsername());
        existing.setProfilePictureUrl(user.getProfilePictureUrl());

        return userRepository.save(existing);
    }
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Ticket addTicketToUser(Long userId, Ticket ticket) {
        User user = getUserById(userId);
        Event event = eventRepository.findById(ticket.getEvent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        int newSoldTickets = event.getSoldTickets() + 1;
        event.setSoldTickets(newSoldTickets);

        char row = (char) ('A' + ((newSoldTickets - 1) / 10));
        int seatNum = ((newSoldTickets - 1) % 10) + 1;
        String generatedSeat = row + Integer.toString(seatNum);

        ticket.setSeatNumber(generatedSeat);
        ticket.setEvent(event);
        ticket.setUser(user);

        user.getTickets().add(ticket);
        return ticket;
    }
    @Override
    public List<TicketDTO> getUserTickets(Long userId) {
        User user = getUserById(userId);
        return user.getTickets().stream()
                .map(TicketMapper::toDto)
                .toList();
    }
}
