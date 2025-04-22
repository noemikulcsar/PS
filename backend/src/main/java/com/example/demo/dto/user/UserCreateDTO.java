package com.example.demo.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO {
    private String name;
    private String email;
    private String username;
    private String password;
}
