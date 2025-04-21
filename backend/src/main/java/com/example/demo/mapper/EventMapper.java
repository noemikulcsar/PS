package com.example.demo.mapper;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;

public class EventMapper {
    public static EventDTO toDto(Event event) {
        if (event == null) return null;

        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .date(event.getDate())
                .venue(VenueMapper.toDto(event.getVenue()))
                .soldTickets(event.getSoldTickets())
                .price(event.getPrice())
                .build();
    }

    public static Event toEntity(EventDTO dto) {
        if (dto == null) return null;

        return Event.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .date(dto.getDate())
                .venue(VenueMapper.toEntity(dto.getVenue()))
                .soldTickets(dto.getSoldTickets())
                .price(dto.getPrice())
                .build();
    }
}