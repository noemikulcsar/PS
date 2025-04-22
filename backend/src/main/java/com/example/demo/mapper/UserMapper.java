package com.example.demo.mapper;

import com.example.demo.dto.user.UserDTO;
import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserProfileDTO;
import com.example.demo.model.User;

public class UserMapper {

    public static UserDTO toDto(User user) {
        if (user == null) return null;
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getUsername());
    }

    public static UserProfileDTO toProfileDto(User user) {
        return new UserProfileDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPhone(),
                user.getAddress(),
                user.getProfilePictureUrl()
        );
    }

    public static User fromProfileDto(UserProfileDTO dto, User existing) {
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setUsername(dto.getUsername());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());
        existing.setProfilePictureUrl(dto.getProfilePictureUrl());
        return existing;
    }

    public static User fromCreateDto(UserCreateDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}