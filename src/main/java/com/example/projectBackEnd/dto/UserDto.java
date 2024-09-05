package com.example.projectBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String userName;
    private String Address;
    private String email;
    private String tel;
    private String image;
    private String password;
}
