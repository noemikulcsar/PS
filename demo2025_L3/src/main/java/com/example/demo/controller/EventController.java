package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.model.Venue;
import com.example.demo.service.EventService;
import com.example.demo.service.VenueService;
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
    private final VenueService venueService;

    public EventController(EventService eventService, VenueService venueService) {
        this.eventService = eventService;
        this.venueService = venueService;
    }

    @GetMapping
    public String listEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
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
        model.addAttribute("event", event);
        return "owners/event-details";
    }

    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return "redirect:/events";
        }
        List<Venue> venues = venueService.getAllVenues();
        model.addAttribute("event", event);
        model.addAttribute("venues", venues);
        return "owners/edit-event";
    }

    @PostMapping("/update")
    public String updateEvent(
            @ModelAttribute Event event,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate,
            @RequestParam("venueId") Long venueId,
            @RequestParam("venue.capacity") int venueCapacity
    ) {
        event.setDate(eventDate);
        Venue venue = venueService.getVenueById(venueId);
        venue.setCapacity(venueCapacity);
        event.setVenue(venue);

        eventService.updateEvent(event);

        return "redirect:/events";
    }

    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("venues", venueService.getAllVenues());
        return "owners/create-event";
    }

    @PostMapping("/create")
    public String createEvent(
            @ModelAttribute Event event,
            @RequestParam("venueId") Long venueId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate
    ) {
        event.setDate(eventDate);
        Venue venue = venueService.getVenueById(venueId);
        event.setVenue(venue);
        eventService.addEvent(event);
        return "redirect:/events";
    }
}
