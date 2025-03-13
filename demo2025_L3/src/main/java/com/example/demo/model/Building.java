package com.example.demo.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Building {
    private Long id;
    private String name;
    private List<Apartment> apartments;
    private CommonSpace commonSpace;


}
