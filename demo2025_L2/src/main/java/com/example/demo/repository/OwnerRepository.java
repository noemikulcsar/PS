package com.example.demo.repository;

import com.example.demo.model.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerRepository {
    private List<Owner> owners = new ArrayList<>();

    //CREATE
    public Owner save (Owner owner) {
        owners.add(owner);
        return owner;
    }

    //READ
    public List<Owner> findAll() {
        return owners;
    }

    public Owner findById(Long id) {
        return owners.stream()
                .filter(owner -> owner.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //UPDATE
    public Owner update(Owner owner) {
        Owner oldOwner = findById(owner.getId());
        if (oldOwner != null) {
            oldOwner.setName(owner.getName());
            oldOwner.setApartments(owner.getApartments());
            return oldOwner;
        }
        return null;

    }

    //DELETE
    public void deleteById(Long id) {
        owners.removeIf(owner -> owner.getId().equals(id));
    }
}
