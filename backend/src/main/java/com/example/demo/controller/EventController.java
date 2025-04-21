package com.example.demo.controller;

import com.example.demo.mapper.VenueMapper;
import com.example.demo.model.Event;
import com.example.demo.model.Venue;
import com.example.demo.service.EventService;
import com.example.demo.service.VenueService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.EventDTO;
import com.example.demo.mapper.EventMapper;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventService eventService;
    private final VenueService venueService;

    public EventController(EventService eventService, VenueService venueService) {
        this.eventService = eventService;
        this.venueService = venueService;
    }

    @PostMapping("/delete/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/details/{id}")
    public EventDTO eventDetails(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return EventMapper.toDto(event);
    }

    @PostMapping("/update")
    public EventDTO updateEvent(@RequestBody EventDTO eventDto) {
        Event event = EventMapper.toEntity(eventDto);
        Venue venue = VenueMapper.toEntity(venueService.getVenueById(eventDto.getVenue().getId()));
        event.setVenue(venue);
        Event updated = eventService.updateEvent(event);
        return EventMapper.toDto(updated);
    }

    @PostMapping("/create")
    public EventDTO createEvent(@RequestBody EventDTO eventDto) {
        Event event = EventMapper.toEntity(eventDto);
        event.setSoldTickets(0);
        Venue venue = VenueMapper.toEntity(venueService.getVenueById(eventDto.getVenue().getId()));
        event.setVenue(venue);
        Event saved = eventService.addEvent(event);
        return EventMapper.toDto(saved);
    }

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }
}
