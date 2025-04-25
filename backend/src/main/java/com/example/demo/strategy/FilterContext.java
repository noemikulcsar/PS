package com.example.demo.strategy;

import com.example.demo.dto.event.EventDetailsDTO;

import java.util.List;

public class FilterContext {

    private EventFilterStrategy strategy;

    public FilterContext(EventFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public List<EventDetailsDTO> filter(List<EventDetailsDTO> events, EventFilterCriteria criteria) {
        return strategy.filter(events, criteria);
    }
}
