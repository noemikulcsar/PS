package com.example.demo.controller;

import com.example.demo.dto.venue.VenueCreateDTO;
import com.example.demo.dto.venue.VenueDTO;
import com.example.demo.service.VenueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Venues", description = "Endpoints related to venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.getVenueById(id));
    }

    @PostMapping
    public ResponseEntity<VenueDTO> addVenue(@RequestBody VenueCreateDTO dto) {
        return ResponseEntity.ok(venueService.addVenue(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(
            @PathVariable Long id,
            @RequestBody VenueCreateDTO dto) {
        return ResponseEntity.ok(venueService.updateVenue(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }
}
