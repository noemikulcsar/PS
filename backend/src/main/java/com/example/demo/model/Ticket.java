package com.example.demo.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    private Long id;
    private String seatNumber;
    private int price;
    private boolean isAvailable;
    private Event event;
}
