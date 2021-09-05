package com.example.namnh.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserLoginRequestDTO {
    private String userName;

    private String password;
}
