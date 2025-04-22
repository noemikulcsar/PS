package com.example.demo.service.impl;

import com.example.demo.dto.ticket.TicketDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;
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
    public List<TicketDetailsDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(TicketMapper::toDetailsDto)
                .toList();
    }

    @Override
    public TicketDetailsDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id));
        return TicketMapper.toDetailsDto(ticket);
    }

    @Override
    public List<TicketDetailsDTO> getUserTickets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return user.getTickets().stream()
                .map(TicketMapper::toDetailsDto)
                .toList();
    }


    @Override
    public TicketDetailsDTO buyTicket(Long eventId, Long userId, String seatNumber) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        int newSoldTickets = event.getSoldTickets() + 1;
        event.setSoldTickets(newSoldTickets);

        char rowLetter = (char) ('A' + ((newSoldTickets - 1) / 10));
        int seatNum = ((newSoldTickets - 1) % 10) + 1;
        String generatedSeat = rowLetter + Integer.toString(seatNum);

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setPrice(event.getPrice());
        ticket.setSeatNumber(generatedSeat);

        Ticket saved = ticketRepository.save(ticket);
        return TicketMapper.toDetailsDto(saved);
    }

    @Override
    public void cancelTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with ID: " + id);
        }
        ticketRepository.deleteById(id);
    }
}
