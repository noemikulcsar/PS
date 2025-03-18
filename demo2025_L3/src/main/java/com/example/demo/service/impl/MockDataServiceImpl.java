package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.model.Venue;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.MockDataService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockDataServiceImpl implements MockDataService {
    private final EventRepository eventRepository;

    public MockDataServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostConstruct
    public void generateMockData() {
        Venue venue1 = new Venue(1L, "Teatrul Național", "Bd. Principal 123", 500);
        Venue venue2 = new Venue(2L, "Opera Română", "Str. Muzicii 45", 800);
        Venue venue3 = new Venue(3L, "Sala Palatului", "Calea Victoriei 60", 1000);
        Venue venue4 = new Venue(4L, "Teatrul de Comedie", "Str. Smârdan 10", 350);

        LocalDate date1 = LocalDate.of(2025, 3, 17);
        LocalDate date2 = LocalDate.of(2025, 4, 30);
        LocalDate date3 = LocalDate.of(2025, 5, 15);
        LocalDate date4 = LocalDate.of(2025, 6, 10);
        LocalDate date5 = LocalDate.of(2025, 7, 5);
        LocalDate date6 = LocalDate.of(2025, 8, 20);

        Event event1 = new Event(1L, "Hamlet", "Tragedie de Shakespeare", date1, venue1, new ArrayList<>());
        Event event2 = new Event(2L, "Lacul Lebedelor", "Balet clasic", date2, venue2, new ArrayList<>());
        Event event3 = new Event(3L, "Carmina Burana", "Spectacol coral-simfonic", date3, venue3, new ArrayList<>());
        Event event4 = new Event(4L, "O scrisoare pierdută", "Comedie de I.L. Caragiale", date4, venue4, new ArrayList<>());
        Event event5 = new Event(5L, "Flaut fermecat", "Operă de Mozart", date5, venue2, new ArrayList<>());
        Event event6 = new Event(6L, "Rigoletto", "Operă de Verdi", date6, venue3, new ArrayList<>());

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
        eventRepository.save(event4);
        eventRepository.save(event5);
        eventRepository.save(event6);
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}
