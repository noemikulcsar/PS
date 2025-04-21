package com.example.demo.service.impl;

import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.TicketMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Event;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user); // JPA updates if ID is already set
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Ticket addTicketToUser(Long userId, Ticket ticket) {
        User user = getUserById(userId);
        Event event = eventRepository.findById(ticket.getEvent().getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));


        int newSoldTickets = event.getSoldTickets() + 1;
        event.setSoldTickets(newSoldTickets);

        char rowLetter = (char) ('A' + ((newSoldTickets - 1) / 10));
        int seatNum = ((newSoldTickets - 1) % 10) + 1;
        String generatedSeat = rowLetter + Integer.toString(seatNum);

        ticket.setSeatNumber(generatedSeat);
        ticket.setUser(user);
        ticket.setEvent(event);

        Ticket saved = ticketRepository.save(ticket);
        user.getTickets().add(saved);
        return saved;
    }
    @Override
    public List<TicketDTO> getUserTickets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getTickets().stream()
                .map(TicketMapper::toDto)
                .toList();
    }
}
