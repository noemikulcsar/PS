package com.example.demo.controller;

import com.example.demo.dto.ticket.TicketDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;
import com.example.demo.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Tickets", description = "Endpoints for ticket operations: purchase, retrieve, and cancel")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDetailsDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailsDTO> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PostMapping("/buy")
    public ResponseEntity<TicketDetailsDTO> buyTicket(
            @RequestParam Long eventId,
            @RequestParam Long userId,
            @RequestParam String seatNumber
    ) {
        return ResponseEntity.ok(ticketService.buyTicket(eventId, userId, seatNumber));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.noContent().build();
    }
}
