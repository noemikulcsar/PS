package com.example.demo.service;

import com.example.demo.dto.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO buyTicket(Long eventId, Long userId, String seatNumber);
    List<TicketDTO> getAllTickets();
    TicketDTO getTicketById(Long id);
    void cancelTicket(Long id);
}
