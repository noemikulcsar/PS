package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MockDataService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MockDataServiceImpl implements MockDataService {
    private final UserRepository userRepository;

    public MockDataServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void generateMockData() {

        User user1 = new User(1L,"noemikulcsar","1234");

        userRepository.save(user1);
    }
}
