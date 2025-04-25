package com.example.demo.dto.event;

import com.example.demo.dto.venue.VenueDTO;
import com.example.demo.model.Venue;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import com.example.demo.export.LocalDateAdapter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDetailsDTO {
    private Long id;
    private String name;
    private String description;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    private int price;
    private int soldTickets;
    private VenueDTO venue;
}

