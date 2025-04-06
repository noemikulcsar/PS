package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event addEvent(Event event) {
        event.setId((long) (eventRepository.findAll().size() + 1));
        return eventRepository.save(event);
    }
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        Event event = eventRepository.findById(id);
        if (event == null) {
            throw new IllegalArgumentException("Event not found with ID: " + id);
        }
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        Event updatedEvent = eventRepository.update(event);
        if (updatedEvent != null) {
            eventRepository.updateVenueCapacity(event.getVenue().getId(), event.getVenue().getCapacity());
        }

        return updatedEvent;
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
