package com.example.demo.dto.event;

import com.example.demo.dto.venue.VenueMapDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventMapDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private VenueMapDTO venue;
}
