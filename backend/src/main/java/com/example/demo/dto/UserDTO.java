package com.example.demo.dto;

import com.example.demo.model.Ticket;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String address;
    private String profilePictureUrl;
}
