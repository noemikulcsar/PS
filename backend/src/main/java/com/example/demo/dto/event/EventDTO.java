package com.example.demo.dto.event;

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
public class EventDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String venueName;
    private int price;
}
