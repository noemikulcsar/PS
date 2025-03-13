package com.example.demo.service.impl;

import com.example.demo.model.Apartment;
import com.example.demo.model.Owner;
import com.example.demo.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class OwnerServiceImplTest {
    private static final String NAME = "TestOwner";

    private Owner owner = new Owner(1L, NAME, null);

    //UUT
    private OwnerServiceImpl ownerService;

    @Mock
    private OwnerRepository ownerRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        ownerService = new OwnerServiceImpl(ownerRepository);
    }


    @Test
    public void givenExistingOwner_whenFindOwnerById_thenReturnOwner() {
        //given
        when(ownerRepository.findById(1L)).thenReturn(owner);

        //when
        Owner resultOwner = ownerService.getOwnerById(1L);

        //then
        assertNotNull(resultOwner);
        assertEquals(NAME, resultOwner.getName());
        verify(ownerRepository, times(1)).findById(1L);
        verify(ownerRepository, times(0)).findAll();
    }

    @Test
    public void givenNonExistingOwner_whenFindOwnerById_thenThrowException() {
        assertThrows(NoSuchElementException.class, () -> {
            ownerService.getOwnerById(1L);
        });
    }

    @Test
    void givenValidOwner_whenUpdateOwner_thenReturnUpdatedOwner() {
        //given
        when(ownerRepository.findById(1L)).thenReturn(owner);
        Owner updatedOwner = new Owner(1L, "Updated Name", List.of(new Apartment()));
        when(ownerRepository.update(any(Owner.class))).thenReturn(updatedOwner);

        //when
        Owner result = ownerService.updateOwner(updatedOwner);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals(1, result.getApartments().size());
    }

}