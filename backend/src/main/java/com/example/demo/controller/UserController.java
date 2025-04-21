package com.example.demo.controller;

import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
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
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Users", description = "Operations related to users and their tickets")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add a new user")
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @Operation(summary = "Get all users (DTO)")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Update user information")
    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @Operation(summary = "Update a specific user by ID")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserDTO updateUser(
            @Parameter(description = "ID of the user") @PathVariable Long id,
            @RequestBody User updatedUser) {

        User user = userService.getUserById(id);
        if (user == null) throw new RuntimeException("User not found");

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());
        user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());

        return UserMapper.toDto(user);
    }

    @Operation(summary = "Delete a user by ID")
    @DeleteMapping("/{id}")
    public void deleteUser(@Parameter(description = "ID of the user") @PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Get a user by ID (DTO)")
    @GetMapping("/{id}")
    public UserDTO getUserById(@Parameter(description = "ID of the user") @PathVariable Long id) {
        return UserMapper.toDto(userService.getUserById(id));
    }

    @Operation(summary = "Get all tickets associated with a user")
    @GetMapping("/{id}/tickets")
    public List<TicketDTO> getUserTickets(@Parameter(description = "ID of the user") @PathVariable Long id) {
        return userService.getUserTickets(id);
    }

    @Operation(summary = "Add a ticket to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket added to user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/{id}/tickets")
    public ResponseEntity<Ticket> addTicketToUser(
            @Parameter(description = "ID of the user") @PathVariable Long id,
            @RequestBody Ticket ticket) {
        try {
            Ticket addedTicket = userService.addTicketToUser(id, ticket);
            return ResponseEntity.ok(addedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
