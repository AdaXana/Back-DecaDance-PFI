package com.decadance.Back_DecaDance_PFI.service;

import org.springframework.stereotype.Service;

import com.decadance.Back_DecaDance_PFI.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // @Override
    

}
