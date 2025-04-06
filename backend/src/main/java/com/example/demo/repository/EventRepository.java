package com.example.demo.repository;

import com.example.demo.model.Event;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventRepository {
    private final List<Event> events = new ArrayList<>();

    public Event save(Event event) {
        Event existingEvent = findById(event.getId());
        if (existingEvent == null) {
            events.add(event);
        } else {
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setVenue(event.getVenue());
            existingEvent.setDate(event.getDate());
            existingEvent.setPrice(event.getPrice());
        }
        return event;
    }


    public List<Event> findAll() {
        return events;
    }

    public Event findById(Long id) {
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public List<Event> findAllByVenueId(Long venueId) {
        return events.stream()
                .filter(event -> event.getVenue().getId().equals(venueId))
                .toList();
    }
    public void updateVenueCapacity(Long venueId, int newCapacity) {
        events.forEach(event -> {
            if (event.getVenue().getId().equals(venueId)) {
                event.getVenue().setCapacity(newCapacity);
            }
        });
    }
    public Event update(Event updatedEvent) {
        Event existingEvent = findById(updatedEvent.getId());
        if (existingEvent != null) {
            existingEvent.setName(updatedEvent.getName());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setVenue(updatedEvent.getVenue());
            existingEvent.setDate(updatedEvent.getDate());
            existingEvent.setPrice(updatedEvent.getPrice());
            existingEvent.setSoldTickets(updatedEvent.getSoldTickets());
            return existingEvent;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return events.removeIf(event -> event.getId().equals(id));
    }
}
