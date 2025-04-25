package com.example.demo.validators;

import com.example.demo.annotations.UniqueUsername;
import com.example.demo.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private static UserRepository userRepository;

    public static void setUserRepository(UserRepository repo) {
        userRepository = repo;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || userRepository == null) {
            return true; // valid by default
        }
        return !userRepository.existsByUsername(username);
    }
}
