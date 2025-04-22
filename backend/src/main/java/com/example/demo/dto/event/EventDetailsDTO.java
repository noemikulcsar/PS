package com.example.demo.dto.event;

import com.example.demo.dto.venue.VenueDTO;
import com.example.demo.model.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private int price;
    private int soldTickets;
    private VenueDTO venue;
}

