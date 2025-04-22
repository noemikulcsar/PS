package com.example.demo.service;

import com.example.demo.dto.event.EventCreateDTO;
import com.example.demo.dto.event.EventDTO;
import com.example.demo.dto.event.EventDetailsDTO;
import com.example.demo.dto.event.EventMapDTO;

import java.util.List;

public interface EventService {
    EventDetailsDTO getEventById(Long id);
    List<EventDetailsDTO> getAllEvents();
    EventDetailsDTO addEvent(EventCreateDTO dto);
    EventDetailsDTO updateEvent(Long id, EventCreateDTO dto);
    void deleteEvent(Long id);
    List<EventDetailsDTO> getAllEventDetails();
    List<EventMapDTO> getEventsForMap();
}
