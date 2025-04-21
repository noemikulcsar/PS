package com.example.demo.service;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;

import java.util.List;

public interface EventService {
    Event addEvent(Event event);
    List<EventDTO> getAllEvents();
    Event getEventById(Long id);
    Event updateEvent(Event event);
    void deleteEvent(Long id);
}
