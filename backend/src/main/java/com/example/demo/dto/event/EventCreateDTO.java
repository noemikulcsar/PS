package com.example.demo.dto.event;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateDTO {
    private String name;
    private String description;
    private LocalDate date;
    private int price;
    private Long venueId;
}

