package com.example.demo.service.impl;

import com.example.demo.dto.TicketDTO;
import com.example.demo.mapper.TicketMapper;
import com.example.demo.model.Event;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TicketDTO buyTicket(Long eventId, Long userId, String seatNumber) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        int newSoldTickets = event.getSoldTickets() + 1;
        event.setSoldTickets(newSoldTickets);

        if (seatNumber == null || seatNumber.isBlank()) {
            char rowLetter = (char) ('A' + ((newSoldTickets - 1) / 10));
            int seatIndex = ((newSoldTickets - 1) % 10) + 1;
            seatNumber = rowLetter + Integer.toString(seatIndex);
        }

        Ticket ticket = Ticket.builder()
                .seatNumber(seatNumber)
                .price(event.getPrice())
                .event(event)
                .user(user)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketMapper.toDto(savedTicket);
    }
    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketMapper::toDto)
                .toList();
    }

    @Override
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + id));
        return TicketMapper.toDto(ticket);
    }

    @Override
    public void cancelTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
