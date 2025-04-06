package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepository {
    private List<Ticket> tickets = new ArrayList<>();

    public Ticket save(Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return tickets;
    }

    public Ticket findById(Long id) {
        return tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteById(Long id) {
        return tickets.removeIf(ticket -> ticket.getId().equals(id));
    }
}
