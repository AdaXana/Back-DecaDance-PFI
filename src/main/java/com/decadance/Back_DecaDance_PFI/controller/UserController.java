package com.decadance.Back_DecaDance_PFI.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decadance.Back_DecaDance_PFI.service.UserService;

@RestController
@RequestMapping("api/v1/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // @PostMapping
    // @GetMapping
    // @PatchMapping
    // @DeleteMapping
}