package com.example.demo.service;

import com.example.demo.dto.venue.VenueDTO;
import com.example.demo.dto.venue.VenueCreateDTO;

import java.util.List;

public interface VenueService {
    List<VenueDTO> getAllVenues();
    VenueDTO getVenueById(Long id);
    VenueDTO addVenue(VenueCreateDTO dto);
    VenueDTO updateVenue(Long id, VenueCreateDTO dto);
    void deleteVenue(Long id);
}
