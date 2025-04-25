package com.example.demo.dto.event;

import com.example.demo.dto.venue.VenueMapDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventMapDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private VenueMapDTO venue;
}
