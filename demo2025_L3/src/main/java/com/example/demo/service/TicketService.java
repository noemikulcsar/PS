package com.example.demo.service;

import com.example.demo.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket buyTicket(Long eventId, Long userId, String seatNumber);
    List<Ticket> getAllTickets();
    Ticket getTicketById(Long id);
    void cancelTicket(Long id);
}
