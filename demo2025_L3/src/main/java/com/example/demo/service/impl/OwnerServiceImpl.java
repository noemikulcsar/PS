package com.example.demo.service.impl;

import com.example.demo.model.Owner;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.service.OwnerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Component
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    private final Random random = new Random();


    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner addOwner(Owner owner) {
        if (owner.getId() == null) {
            owner.setId(random.nextLong(Long.MAX_VALUE));
        }
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
