package com.example.demo.mapper;

import com.example.demo.dto.TicketDTO;
import com.example.demo.model.Ticket;

public class TicketMapper {

    public static TicketDTO toDto(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .eventId(ticket.getEvent().getId())
                .event(EventMapper.toDto(ticket.getEvent()))
                .build();
    }
    public static Ticket toEntity(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setSeatNumber(dto.getSeatNumber());
        ticket.setPrice(dto.getPrice());
        return ticket;
    }
}
