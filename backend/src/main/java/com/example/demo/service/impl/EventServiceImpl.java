package com.example.demo.service.impl;

import com.example.demo.dto.event.EventCreateDTO;
import com.example.demo.dto.event.EventDTO;
import com.example.demo.dto.event.EventDetailsDTO;
import com.example.demo.dto.event.EventMapDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EventMapper;
import com.example.demo.model.Event;
import com.example.demo.model.Venue;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.VenueRepository;
import com.example.demo.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventServiceImpl(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public EventDetailsDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
        return EventMapper.toDetailsDto(event);
    }

    @Override
    public List<EventDetailsDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(EventMapper::toDetailsDto)
                .toList();
    }

    @Override
    public EventDetailsDTO addEvent(EventCreateDTO dto) {
        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + dto.getVenueId()));
        Event saved = eventRepository.save(EventMapper.fromCreateDto(dto, venue));
        return EventMapper.toDetailsDto(saved);
    }

    @Override
    public EventDetailsDTO updateEvent(Long id, EventCreateDTO dto) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + dto.getVenueId()));

        existing.setName(dto.getName());
        existing.setDate(dto.getDate());
        existing.setPrice(dto.getPrice());
        existing.setVenue(venue);

        Event updated = eventRepository.save(existing);
        return EventMapper.toDetailsDto(updated);
    }

    @Override
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }
    @Override
    public List<EventDetailsDTO> getAllEventDetails() {
        return eventRepository.findAll()
                .stream()
                .map(EventMapper::toDetailsDto)
                .toList();
    }
    @Override
    public List<EventMapDTO> getEventsForMap() {
        return eventRepository.findAll().stream()
                .map(EventMapper::toMapDto)
                .toList();
    }

}
