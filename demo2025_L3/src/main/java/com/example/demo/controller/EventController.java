package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.model.Venue;
import com.example.demo.service.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String listEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        System.out.println("Evenimente găsite: " + events.size());
        model.addAttribute("events", events);
        return "owners/list-events";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }
    @GetMapping("/details/{id}")
    public String eventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return "redirect:/events";
        }
        System.out.println("Detalii accesate");
        model.addAttribute("event", event);
        return "owners/event-details";
    }
    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return "redirect:/events"; // Evită erori
        }
        model.addAttribute("event", event);
        return "owners/edit-event";
    }
    @PostMapping("/update")
    public String updateEvent(
            @ModelAttribute Event event,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate,
            @RequestParam("venue.id") Long venueId,
            @RequestParam("venue.name") String venueName,
            @RequestParam("venue.address") String venueAddress,
            @RequestParam("venue.capacity") int venueCapacity
    ) {
        event.setDate(eventDate);
        if (event.getVenue() == null) {
            event.setVenue(new Venue());
        }
        event.getVenue().setId(venueId);
        event.getVenue().setName(venueName);
        event.getVenue().setAddress(venueAddress);
        event.getVenue().setCapacity(venueCapacity);
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "owners/create-event";
    }

    @PostMapping("/create")
    public String createEvent(
            @ModelAttribute Event event,
            @RequestParam("venue.name") String venueName,
            @RequestParam("venue.address") String venueAddress,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate,
            @RequestParam("venue.capacity") int venueCapacity
    ) {
        event.setDate(eventDate);
        if (event.getVenue() == null) {
            event.setVenue(new Venue());
        }
        event.getVenue().setName(venueName);
        event.getVenue().setAddress(venueAddress);
        event.getVenue().setCapacity(venueCapacity);
        eventService.addEvent(event);

        return "redirect:/events";
    }

}
