package com.example.projectBackEnd.service;

public interface UserService {
    boolean isUserNameExists(String userName);

    boolean isEmailExists(String email);
}
