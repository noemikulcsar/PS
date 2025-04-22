package com.example.demo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketNotification {
    private String username;
    private String eventName;
    private String seatNumber;
}
