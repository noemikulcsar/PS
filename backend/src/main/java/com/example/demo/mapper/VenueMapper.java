package com.example.demo.mapper;

import com.example.demo.dto.venue.VenueDTO;
import com.example.demo.dto.venue.VenueCreateDTO;
import com.example.demo.model.Venue;
import com.example.demo.dto.venue.VenueMapDTO;

public class VenueMapper {

    public static VenueDTO toDto(Venue venue) {
        if (venue == null) return null;
        return new VenueDTO(venue.getId(), venue.getName(), venue.getAddress(), venue.getCapacity());
    }
    public static VenueMapDTO toMapDto(Venue venue) {
        if (venue == null) return null;
        return new VenueMapDTO(venue.getId(), venue.getName(), venue.getLatitude(), venue.getLongitude());
    }
    public static Venue fromDto(VenueDTO dto) {
        return Venue.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .capacity(dto.getCapacity())
                .build();
    }

    public static Venue fromCreateDto(VenueCreateDTO dto) {
        return Venue.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .capacity(dto.getCapacity())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}