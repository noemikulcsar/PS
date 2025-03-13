package com.example.demo.repository;

import com.example.demo.model.Building;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BuildingRepository {
    private final List<Building> buildings = new ArrayList<>();

    public Building save(Building building) {
        buildings.add(building);
        return building;
    }

    public List<Building> findAll() {
        return buildings;
    }

    public Building findById(Long id) {
        return buildings.stream()
                .filter(building -> building.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Building updateBuilding(Building building) {
        Building oldBuilding = findById(building.getId());

        if (oldBuilding != null) {
            oldBuilding.setName(building.getName());
            oldBuilding.setApartments(building.getApartments());
            oldBuilding.setCommonSpace(building.getCommonSpace());

            return oldBuilding;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return buildings.removeIf(building -> building.getId().equals(id));
    }
}
