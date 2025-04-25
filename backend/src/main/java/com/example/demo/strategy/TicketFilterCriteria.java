package com.example.demo.strategy;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TicketFilterCriteria {
    private Integer minPrice;
    private Integer maxPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> locations;
    private String sortOption;
}
