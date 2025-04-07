
package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.UserService;
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User authenticate(String username, String password) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    @Override
    public Ticket addTicketToUser(Long userId, Ticket ticket) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Event event = eventRepository.findById(ticket.getEvent().getId());
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        int newSoldTickets = event.getSoldTickets() + 1;
        event.setSoldTickets(newSoldTickets);

        char rowLetter = (char) ('A' + ((newSoldTickets - 1) / 10));
        int seatNumber = ((newSoldTickets - 1) % 10) + 1;
        String generatedSeat = rowLetter + Integer.toString(seatNumber);

        ticket.setId(System.currentTimeMillis());
        ticket.setSeatNumber(generatedSeat);
        ticket.setEvent(event);
        ticket.setAvailable(false);

        user.getTickets().add(ticket);
        return ticket;
    }
}
