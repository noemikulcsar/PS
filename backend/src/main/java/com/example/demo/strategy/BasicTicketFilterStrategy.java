package com.example.demo.strategy;

import com.example.demo.dto.ticket.TicketDetailsDTO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BasicTicketFilterStrategy implements TicketFilterStrategy {

    @Override
    public List<TicketDetailsDTO> filter(List<TicketDetailsDTO> tickets, TicketFilterCriteria criteria) {
        return tickets.stream()
                .filter(ticket -> {
                    // Validări defensive (în caz că lipsesc datele din DTO)
                    if (ticket.getEvent() == null || ticket.getEvent().getDate() == null) return false;

                    boolean matchesPrice =
                            (criteria.getMinPrice() == null || ticket.getPrice() >= criteria.getMinPrice()) &&
                                    (criteria.getMaxPrice() == null || ticket.getPrice() <= criteria.getMaxPrice());

                    boolean matchesDate =
                            (criteria.getStartDate() == null || !ticket.getEvent().getDate().isBefore(criteria.getStartDate())) &&
                                    (criteria.getEndDate() == null || !ticket.getEvent().getDate().isAfter(criteria.getEndDate()));

                    boolean matchesLocation =
                            criteria.getLocations() == null || criteria.getLocations().isEmpty() ||
                                    (ticket.getEvent().getVenue() != null &&
                                            criteria.getLocations().contains(ticket.getEvent().getVenue().getName()));

                    return matchesPrice && matchesDate && matchesLocation;
                })
                .sorted(getComparator(criteria.getSortOption()))
                .collect(Collectors.toList());
    }

    private Comparator<TicketDetailsDTO> getComparator(String sortOption) {
        if (sortOption == null) return (a, b) -> 0;

        return switch (sortOption) {
            case "priceAsc" -> Comparator.comparingInt(TicketDetailsDTO::getPrice);
            case "priceDesc" -> Comparator.comparingInt(TicketDetailsDTO::getPrice).reversed();
            case "dateAsc" -> Comparator.comparing(t -> t.getEvent().getDate());
            case "dateDesc" -> Comparator.comparing((TicketDetailsDTO t) -> t.getEvent().getDate()).reversed();
            default -> (a, b) -> 0;
        };
    }
}
