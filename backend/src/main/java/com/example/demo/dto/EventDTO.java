package com.example.demo.dto;

import com.example.demo.model.Venue;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private VenueDTO venue;
    private int soldTickets;
    private int price;
}
