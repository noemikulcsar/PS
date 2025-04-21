package com.example.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {
    private Long id;
    private String seatNumber;
    private int price;
    private Long eventId;
    private EventDTO event;}
