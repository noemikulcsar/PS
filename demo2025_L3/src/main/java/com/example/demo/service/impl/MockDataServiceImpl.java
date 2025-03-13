package com.example.demo.service.impl;

import com.example.demo.constants.ApartmentType;
import com.example.demo.model.*;
import com.example.demo.repository.ApartmentRepository;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.CommonSpaceRepository;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.service.MockDataService;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MockDataServiceImpl implements MockDataService {
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final BuildingRepository buildingRepository;
    private final ApartmentRepository apartmentRepository;
    private final OwnerRepository ownerRepository;
    private final CommonSpaceRepository commonSpaceRepository;

    public MockDataServiceImpl(BuildingRepository buildingRepository,
                               ApartmentRepository apartmentRepository,
                               OwnerRepository ownerRepository,
                               CommonSpaceRepository commonSpaceRepository) {
        this.buildingRepository = buildingRepository;
        this.apartmentRepository = apartmentRepository;
        this.ownerRepository = ownerRepository;
        this.commonSpaceRepository = commonSpaceRepository;
    }

    @PostConstruct
    public void init() {
        generateMockData();
    }

    @Override
    public void generateMockData() {
        List<Building> buildings = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Building building = new Building();
            building.setId((long) i);
            building.setName(faker.address().buildingNumber() + " " + faker.address().streetName());

            List<Apartment> apartments = new ArrayList<>();
            List<Owner> owners = new ArrayList<>();

            for (int j = 1; j <= 5; j++) {
                Apartment apartment = new Apartment();
                apartment.setId((long) (i * 10 + j));
                apartment.setNumber(String.valueOf(100 + j));
                apartment.setApartmentType(ApartmentType.values()[random.nextInt(ApartmentType.values().length)]);

                List<Owner> apartmentOwners = new ArrayList<>();
                for (int k = 1; k <= random.nextInt(3) + 1; k++) {
                    Owner owner = new Owner();
                    owner.setId((long) (i * 100 + j * 10 + k));
                    owner.setName(faker.name().fullName());
                    owner.setApartments(new ArrayList<>());
                    apartmentOwners.add(owner);
                    owners.add(owner);
                }
                apartment.setOwners(apartmentOwners);
                apartments.add(apartment);
            }

            building.setApartments(apartments);
            building.setCommonSpace(generateRandomCommonSpace());
            buildings.add(building);

            apartmentRepository.saveAll(apartments);
            ownerRepository.saveAll(owners);
            buildingRepository.save(building);
            commonSpaceRepository.save(building.getCommonSpace());
        }
    }

    private CommonSpace generateRandomCommonSpace() {
        if (random.nextBoolean()) {
            Parking parking = new Parking();
            parking.setId(faker.number().randomNumber());
            parking.setSurface(faker.number().numberBetween(50, 500));
            parking.setNoOfSpots((long) faker.number().numberBetween(10, 100));
            return parking;
        } else {
            Terrace terrace = new Terrace();
            terrace.setId(faker.number().randomNumber());
            terrace.setSurface(faker.number().numberBetween(30, 300));
            terrace.setHasPool(random.nextBoolean());
            return terrace;
        }
    }

    public List<Building> getBuildings() {
        return buildingRepository.findAll();
    }
}
