package com.decadance.Back_DecaDance_PFI.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decadance.Back_DecaDance_PFI.entity.User;
import com.decadance.Back_DecaDance_PFI.service.UserService;

@RestController
@RequestMapping("api/v1/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

     @GetMapping("/username/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        return new ResponseEntity<>(userService.getUserByUsername(name), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return userService.getUsers();
    }

    // @PostMapping
    // @PatchMapping
    // @DeleteMapping
}