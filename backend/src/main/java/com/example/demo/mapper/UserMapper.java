package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

public class UserMapper {

    public static UserDTO toDto(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .profilePictureUrl(dto.getProfilePictureUrl())
                .build();
    }
}
