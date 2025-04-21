package com.example.demo.controller;

import com.example.demo.dto.TicketDTO;
import com.example.demo.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets", description = "Endpoints for ticket operations: purchase, retrieve, and cancel")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "Buy a ticket for an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket purchased successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters or ticket unavailable"),
            @ApiResponse(responseCode = "404", description = "User or Event not found")
    })
    @PostMapping("/buy")
    public ResponseEntity<TicketDTO> buyTicket(
            @Parameter(description = "ID of the event") @RequestParam Long eventId,
            @Parameter(description = "ID of the user") @RequestParam Long userId,
            @Parameter(description = "Seat number to reserve (optional)") @RequestParam(required = false) String seatNumber) {
        try {
            TicketDTO ticket = ticketService.buyTicket(eventId, userId, seatNumber);
            return ResponseEntity.ok(ticket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all tickets")
    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @Operation(summary = "Get a ticket by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket found"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(
            @Parameter(description = "ID of the ticket to retrieve") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(ticketService.getTicketById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Cancel a ticket by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket canceled successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelTicket(
            @Parameter(description = "ID of the ticket to cancel") @PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.ok().build();
    }
}
