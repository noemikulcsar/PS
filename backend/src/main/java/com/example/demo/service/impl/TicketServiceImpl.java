package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Ticket buyTicket(Long eventId, Long userId, String seatNumber) {
        Event event = eventRepository.findById(eventId);
        User user = userRepository.findById(userId);

        if (event == null || user == null) {
            throw new IllegalArgumentException("Event or User not found");
        }

        Ticket ticket = new Ticket();
        ticket.setId(System.currentTimeMillis());
        ticket.setSeatNumber(seatNumber);
        ticket.setPrice(event.getPrice());
        ticket.setAvailable(false);
        ticket.setEvent(event);

        event.setSoldTickets(event.getSoldTickets() + 1);
        Ticket savedTicket = ticketRepository.save(ticket);
        user.getTickets().add(savedTicket);
        return savedTicket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public void cancelTicket(Long id) {
        ticketRepository.deleteById(id);
    }

}
