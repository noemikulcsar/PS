package com.example.demo.controller;

import com.example.demo.dto.event.EventCreateDTO;
import com.example.demo.dto.event.EventDTO;
import com.example.demo.dto.event.EventDetailsDTO;
import com.example.demo.dto.event.EventMapDTO;
import com.example.demo.mapper.EventMapper;
import com.example.demo.service.EventService;
import com.example.demo.strategy.CombinedFilterStrategy;
import com.example.demo.strategy.EventFilterCriteria;
import com.example.demo.strategy.FilterContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Events", description = "Operations related to events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDetailsDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEventDetails());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<EventDetailsDTO> eventDetails(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EventDetailsDTO> createEvent(@Valid @RequestBody EventCreateDTO dto) {
        EventDetailsDTO savedEvent = eventService.addEvent(dto);
        return ResponseEntity.ok(savedEvent);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<EventDetailsDTO> updateEvent(@PathVariable Long id, @RequestBody EventCreateDTO dto) {
        return ResponseEntity.ok(eventService.updateEvent(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/map")
    public ResponseEntity<List<EventMapDTO>> getEventsForMap() {
        return ResponseEntity.ok(eventService.getEventsForMap());
    }
    @PostMapping("/filter")
    public ResponseEntity<List<EventDetailsDTO>> getFilteredEvents(@RequestBody EventFilterCriteria criteria) {
        List<EventDetailsDTO> allEvents = eventService.getAllEventDetails();
        FilterContext context = new FilterContext(new CombinedFilterStrategy());
        List<EventDetailsDTO> filtered = context.filter(allEvents, criteria);
        return ResponseEntity.ok(filtered);
    }
}
