package com.example.demo.validators;

import com.example.demo.repository.UserRepository;
import com.example.demo.validators.UniqueUsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class ValidatorInjector {
    private UserRepository userRepository;

    @PostConstruct
    public void inject() {
        UniqueUsernameValidator.setUserRepository(userRepository);
    }
}
