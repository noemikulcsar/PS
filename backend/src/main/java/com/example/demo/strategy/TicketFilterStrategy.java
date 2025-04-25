package com.example.demo.strategy;

import com.example.demo.dto.ticket.TicketDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;

import java.util.List;

public interface TicketFilterStrategy {
    List<TicketDetailsDTO> filter(List<TicketDetailsDTO> tickets, TicketFilterCriteria criteria);
}
