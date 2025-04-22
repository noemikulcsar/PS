package com.example.demo.dto.ticket;

import com.example.demo.dto.event.EventDTO;
import com.example.demo.dto.event.EventDetailsDTO;
import com.example.demo.dto.user.UserDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailsDTO {
    private Long id;
    private String seatNumber;
    private int price;
    private EventDetailsDTO event;
    private UserDTO user;
}
