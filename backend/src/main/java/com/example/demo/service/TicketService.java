package com.example.demo.service;

import com.example.demo.dto.ticket.TicketDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;

import java.util.List;

public interface TicketService {

    List<TicketDetailsDTO> getAllTickets();

    TicketDetailsDTO getTicketById(Long id);

    List<TicketDetailsDTO> getUserTickets(Long userId);

    TicketDetailsDTO buyTicket(Long eventId, Long userId, String seatNumber);

    void cancelTicket(Long id);
}
