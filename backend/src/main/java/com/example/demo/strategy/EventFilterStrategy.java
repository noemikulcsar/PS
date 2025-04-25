package com.example.demo.strategy;

import com.example.demo.dto.event.EventDetailsDTO;

import java.util.List;

public interface EventFilterStrategy {
    List<EventDetailsDTO> filter(List<EventDetailsDTO> events, EventFilterCriteria criteria);
}
