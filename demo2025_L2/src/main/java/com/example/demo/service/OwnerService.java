package com.example.demo.service;

import com.example.demo.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OwnerService {
    Owner addOwner(Owner owner);
    List<Owner> getAllOwners();
    Owner getOwnerById(Long id);
    Owner updateOwner(Owner owner);
    void deleteOwner(Long id);

}
