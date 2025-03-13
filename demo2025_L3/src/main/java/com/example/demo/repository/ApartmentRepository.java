package com.example.demo.repository;

import com.example.demo.model.Apartment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApartmentRepository {
    private final List<Apartment> apartments = new ArrayList<>();

    public Apartment save(Apartment apartment) {
        apartments.add(apartment);
        return apartment;
    }

    public void saveAll(List<Apartment> newApartments) {
        apartments.addAll(newApartments);
    }

    public List<Apartment> findAll() {
        return apartments;
    }

    public Apartment findById(Long id) {
        return apartments.stream()
                .filter(apartment -> apartment.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Apartment updateApartment(Apartment apartment) {
        Apartment oldApartment = findById(apartment.getId());

        if (oldApartment != null) {
            oldApartment.setNumber(apartment.getNumber());
            oldApartment.setApartmentType(apartment.getApartmentType());

            return oldApartment;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return apartments.removeIf(apartment -> apartment.getId().equals(id));
    }
}

