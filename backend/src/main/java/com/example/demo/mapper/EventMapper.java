package com.example.demo.mapper;

import com.example.demo.dto.event.EventDTO;
import com.example.demo.dto.event.EventCreateDTO;
import com.example.demo.dto.event.EventDetailsDTO;
import com.example.demo.dto.event.EventMapDTO;
import com.example.demo.model.Event;
import com.example.demo.model.Venue;

public class EventMapper {

    public static EventDTO toDto(Event event) {
        if (event == null) return null;
        return new EventDTO(event.getId(), event.getName(), event.getDate(), event.getVenue().getName(), event.getPrice());
    }

    public static EventDetailsDTO toDetailsDto(Event event) {
        if (event == null) return null;
        return new EventDetailsDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getPrice(),
                event.getSoldTickets(),
                VenueMapper.toDto(event.getVenue())
        );
    }

    public static Event fromCreateDto(EventCreateDTO dto, Venue venue) {
        return Event.builder()
                .name(dto.getName())
                .date(dto.getDate())
                .price(dto.getPrice())
                .soldTickets(0)
                .venue(venue)
                .build();
    }
    public static EventMapDTO toMapDto(Event event) {
        if (event == null) return null;
        return new EventMapDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                VenueMapper.toMapDto(event.getVenue())
        );
    }

}