package com.example.namnh.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDTO {
    private String userName;

    private String name;

    private Date lastLogin;

    private String role;
}
