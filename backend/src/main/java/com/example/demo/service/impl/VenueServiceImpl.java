package com.example.demo.service.impl;

import com.example.demo.model.Venue;
import com.example.demo.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private final List<Venue> venues = Arrays.asList(
            new Venue(1L, "Teatrul Național", "Bd. Principal 123", 100, 46.770439, 23.591423),
            new Venue(2L, "Opera Română", "Str. Muzicii 45", 70, 46.768224, 23.583485),
            new Venue(3L, "Sala Palatului", "Calea Victoriei 60", 80, 44.439663, 26.096306),
            new Venue(4L, "Teatrul de Comedie", "Str. Smârdan 10", 75, 44.432813, 26.104859)
    );

    @Override
    public List<Venue> getAllVenues() {
        return venues;
    }

    @Override
    public Venue getVenueById(Long id) {
        return venues.stream()
                .filter(venue -> venue.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
