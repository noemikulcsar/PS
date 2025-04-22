package com.example.demo.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String address;
    private String profilePictureUrl;
}


