package com.example.demo.model;

import com.example.demo.constants.ApartmentType;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Apartment {
    private Long id;
    private String number;
    private ApartmentType apartmentType;
    private List<Owner> owners;

}
