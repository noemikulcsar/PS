package com.example.demo.controller;

import com.example.demo.dto.ticket.TicketDetailsDTO;
import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.dto.user.UserProfileDTO;
import com.example.demo.export.TicketListXmlWrapper;
import com.example.demo.model.Event;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.strategy.BasicTicketFilterStrategy;
import com.example.demo.strategy.TicketFilterCriteria;
import com.example.demo.strategy.TicketFilterStrategy;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.ticket.TicketCreateDTO;
import java.util.List;
import com.example.demo.service.FavoriteEventService;
import com.example.demo.repository.EventRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Users", description = "Operations related to users and their tickets")
public class UserController {

    private final UserService userService;
    private final FavoriteEventService favoriteEventService;
    private final EventRepository eventRepository;

    public UserController(UserService userService, FavoriteEventService favoriteEventService, EventRepository eventRepository) {
        this.userService = userService;
        this.favoriteEventService = favoriteEventService;
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.addUser(dto));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfile(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserProfileDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketDetailsDTO>> getUserTickets(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserTickets(id));
    }
    @PostMapping("/{id}/tickets")
    public ResponseEntity<TicketDetailsDTO> addTicketToUser(
            @PathVariable Long id,
            @RequestBody TicketCreateDTO ticketDto) {
        TicketDetailsDTO ticket = userService.addTicketToUser(id, ticketDto);
        return ResponseEntity.ok(ticket);
    }
    @PostMapping("/{userId}/favorites/{eventId}")
    public ResponseEntity<?> addFavorite(@PathVariable Long userId, @PathVariable Long eventId) {
        User user = userService.getUserById(userId);
        Event event = eventRepository.findById(eventId).orElseThrow();
        favoriteEventService.addFavorite(user, event);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/favorites/{eventId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long userId, @PathVariable Long eventId) {
        User user = userService.getUserById(userId);
        Event event = eventRepository.findById(eventId).orElseThrow();
        favoriteEventService.removeFavorite(user, event);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Event>> getFavorites(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(favoriteEventService.getFavoritesForUser(user));
    }

    @GetMapping("/{userId}/favorites/{eventId}")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long userId, @PathVariable Long eventId) {
        User user = userService.getUserById(userId);
        Event event = eventRepository.findById(eventId).orElseThrow();
        return ResponseEntity.ok(favoriteEventService.isFavorite(user, event));
    }
    @PostMapping("/{userId}/tickets/filter")
    public ResponseEntity<List<TicketDetailsDTO>> filterTickets(
            @PathVariable Long userId,
            @RequestBody TicketFilterCriteria criteria) {

        List<TicketDetailsDTO> allTickets = userService.getUserTickets(userId);
        TicketFilterStrategy strategy = new BasicTicketFilterStrategy();
        List<TicketDetailsDTO> filtered = strategy.filter(allTickets, criteria);
        return ResponseEntity.ok(filtered);
    }
    @GetMapping(value = "/{userId}/tickets/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<TicketListXmlWrapper> exportTicketsToXml(@PathVariable Long userId) {
        List<TicketDetailsDTO> tickets = userService.getUserTickets(userId);
        return ResponseEntity.ok(new TicketListXmlWrapper(tickets));
    }
}
