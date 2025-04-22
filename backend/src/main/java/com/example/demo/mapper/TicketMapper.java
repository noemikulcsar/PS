package com.example.demo.mapper;

import com.example.demo.dto.ticket.TicketDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;
import com.example.demo.model.Ticket;

public class TicketMapper {

    public static TicketDTO toDto(Ticket ticket) {
        if (ticket == null) return null;
        return new TicketDTO(
                ticket.getId(),
                ticket.getSeatNumber(),
                ticket.getPrice(),
                ticket.getEvent() != null ? ticket.getEvent().getName() : null,
                ticket.getUser() != null ? ticket.getUser().getUsername() : null
        );
    }
    public static TicketDetailsDTO toDetailsDto(Ticket ticket) {
        return TicketDetailsDTO.builder()
                .id(ticket.getId())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .event(EventMapper.toDetailsDto(ticket.getEvent()))
                .user(UserMapper.toDto(ticket.getUser()))
                .build();
    }

}