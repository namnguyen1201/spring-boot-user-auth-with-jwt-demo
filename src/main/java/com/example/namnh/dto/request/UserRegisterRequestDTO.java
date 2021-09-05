package com.example.namnh.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRegisterRequestDTO {
    private String userName;

    private String password;

    private String name;
}
