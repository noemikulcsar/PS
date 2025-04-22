package com.example.demo.service.impl;

import com.example.demo.dto.TicketDTO;
import com.example.demo.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        int newSoldTickets = event.getSoldTickets() + 1;
        event.setSoldTickets(newSoldTickets);

        char row = (char) ('A' + ((newSoldTickets - 1) / 10));
        int seatNum = ((newSoldTickets - 1) % 10) + 1;
        String generatedSeat = row + Integer.toString(seatNum);

        Ticket ticket = new Ticket();
        ticket.setSeatNumber(generatedSeat);
        ticket.setPrice(event.getPrice());
        ticket.setEvent(event);
        ticket.setUser(user);

        Ticket saved = ticketRepository.save(ticket);
        return TicketMapper.toDto(saved);
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(TicketMapper::toDto)
                .toList();
    }

    @Override
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id));
        return TicketMapper.toDto(ticket);
    }

    @Override
    public void cancelTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with ID: " + id);
        }
        ticketRepository.deleteById(id);
    }
}
