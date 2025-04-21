package com.example.demo.service;

import com.example.demo.dto.VenueDTO;

import java.util.List;

public interface VenueService {
    List<VenueDTO> getAllVenues();
    VenueDTO getVenueById(Long id);
    VenueDTO addVenue(VenueDTO dto);
    VenueDTO updateVenue(Long id, VenueDTO dto);
    void deleteVenue(Long id);
}
