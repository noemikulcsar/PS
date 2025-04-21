package com.example.demo.mapper;

import com.example.demo.dto.VenueDTO;
import com.example.demo.model.Venue;

public class VenueMapper {

    public static VenueDTO toDto(Venue venue) {
        return VenueDTO.builder()
                .id(venue.getId())
                .name(venue.getName())
                .address(venue.getAddress())
                .capacity(venue.getCapacity())
                .latitude(venue.getLatitude())
                .longitude(venue.getLongitude())
                .build();
    }

    public static Venue toEntity(VenueDTO dto) {
        return Venue.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .capacity(dto.getCapacity())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}