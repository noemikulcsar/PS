package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MockDataService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;

@Service
public class MockDataServiceImpl implements MockDataService {
    private final UserRepository userRepository;

    public MockDataServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void generateMockData() {

        User user1 = new User(1L,"Noemi Kulcsar","noemikulcsar@yahoo.com","noemikulcsar","1234","0754617850","Strada Aleea Zorilor, Numarul 6, Bloc A-4, Scara 3, Ap. 2","/assets/profile-page.jpeg");

        userRepository.save(user1);
    }
}
