package com.example.demo.service;

import com.example.demo.model.Venue;
import java.util.List;

public interface VenueService {
    List<Venue> getAllVenues();
    Venue getVenueById(Long id);
}
