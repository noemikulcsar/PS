package com.example.demo.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Venue {
    private Long id;
    private String name;
    private String address;
    private int capacity;
}
