package com.example.demo.controller;

import com.example.demo.dto.ticket.TicketDTO;
import com.example.demo.dto.ticket.TicketDetailsDTO;
import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.dto.user.UserProfileDTO;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.ticket.TicketCreateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Users", description = "Operations related to users and their tickets")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
