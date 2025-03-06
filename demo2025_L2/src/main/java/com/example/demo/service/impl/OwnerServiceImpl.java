package com.example.demo.service.impl;

import com.example.demo.model.Owner;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.service.OwnerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner addOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner getOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id);
        if (owner != null) {
            return owner;
        }
        throw new NoSuchElementException("Owner with id" + id + "not found");
    }

    @Override
    public Owner updateOwner(Owner owner) {
        return ownerRepository.update(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
