package com.example.demo.strategy;

import com.example.demo.dto.event.EventDetailsDTO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CombinedFilterStrategy implements EventFilterStrategy {

    @Override
    public List<EventDetailsDTO> filter(List<EventDetailsDTO> events, EventFilterCriteria criteria) {
        LocalDate today = LocalDate.now();
        return events.stream()
                .filter(event -> {
                    boolean priceOK = (criteria.getMinPrice() == null || event.getPrice() >= criteria.getMinPrice()) &&
                            (criteria.getMaxPrice() == null || event.getPrice() <= criteria.getMaxPrice());

                    boolean dateOK = (criteria.getStartDate() == null || !event.getDate().isBefore(criteria.getStartDate())) &&
                            (criteria.getEndDate() == null || !event.getDate().isAfter(criteria.getEndDate()));

                    boolean locationOK = criteria.getLocations() == null || criteria.getLocations().isEmpty() ||
                            (event.getVenue() != null && criteria.getLocations().contains(event.getVenue().getName()));

                    boolean availableOK = !criteria.isOnlyAvailable() ||
                            (event.getVenue() != null && event.getVenue().getCapacity() > event.getSoldTickets());

                    return priceOK && dateOK && locationOK && availableOK;
                })
                .sorted(getComparator(criteria.getSortOption()))
                .collect(Collectors.toList());
    }

    private Comparator<EventDetailsDTO> getComparator(String sortOption) {
        if (sortOption == null) return (a, b) -> 0;

        return switch (sortOption) {
            case "priceAsc" -> Comparator.comparingInt(EventDetailsDTO::getPrice);
            case "priceDesc" -> Comparator.comparingInt(EventDetailsDTO::getPrice).reversed();
            case "dateAsc" -> Comparator.comparing(EventDetailsDTO::getDate);
            case "dateDesc" -> Comparator.comparing(EventDetailsDTO::getDate).reversed();
            default -> (a, b) -> 0;
        };
    }
}
