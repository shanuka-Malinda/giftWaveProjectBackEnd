package com.example.projectBackEnd.controller;

import com.example.projectBackEnd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check-username")
    public boolean checkUserName(@RequestParam String userName) {
        return userService.isUserNameExists(userName);
    }
    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam String email) {
        return userService.isEmailExists(email);
    }
}
