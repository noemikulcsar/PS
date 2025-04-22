package com.example.demo.service.impl;

import com.example.demo.dto.VenueDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.VenueMapper;
import com.example.demo.model.Venue;
import com.example.demo.repository.VenueRepository;
import com.example.demo.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;

    public VenueServiceImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll()
                .stream()
                .map(VenueMapper::toDto)
                .toList();
    }

    @Override
    public VenueDTO getVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + id));
        return VenueMapper.toDto(venue);
    }

    @Override
    public VenueDTO addVenue(VenueDTO dto) {
        Venue venue = VenueMapper.toEntity(dto);
        Venue saved = venueRepository.save(venue);
        return VenueMapper.toDto(saved);
    }

    @Override
    public VenueDTO updateVenue(Long id, VenueDTO dto) {
        Venue existing = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setCapacity(dto.getCapacity());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());

        Venue updated = venueRepository.save(existing);
        return VenueMapper.toDto(updated);
    }

    @Override
    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venue not found with ID: " + id);
        }
        venueRepository.deleteById(id);
    }
}
