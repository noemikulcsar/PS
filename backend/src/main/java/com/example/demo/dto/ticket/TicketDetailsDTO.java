package com.example.demo.dto.ticket;

import com.example.demo.dto.event.EventDetailsDTO;
import com.example.demo.dto.user.UserDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketDetailsDTO {

    private Long id;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @Min(value = 1, message = "Price must be greater than 0")
    private int price;

    @NotNull(message = "Event details are required")
    private EventDetailsDTO event;

    @NotNull(message = "User details are required")
    private UserDTO user;
}